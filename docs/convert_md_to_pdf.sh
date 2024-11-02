#!/bin/bash

# 2024-08-26, Michael Mueller
# A Script, which converts all Markup (.md) files in a directory and it's subdirectory to corresponding PDFs
# into the subfolder /pdf/.
# If a Markup file contains an SVG, it is first converted to PDF and then replaced in a temporary Markdown file,
# in order to properly convert the Markup into the PDF.
# Finally merges all PDFs into a single one /pdf/all_created_pdfs_in_one.pdf

# Requires pandoc, latex tools and either (depending on which one is used in process_markdown_file):
  # Inkscape
  # rsvg-convert ( sudo apt-get install librsvg2-bin libxml2-utils)
  # cairosvg ( sudo apt-get install python3-cairosvg )

# Base directory containing .md files
BASE_DIR="./"

# Create /pdf/ subdirectory, if it does not exists
OUTPUT_DIR="${BASE_DIR}/pdf"
mkdir -p "${OUTPUT_DIR}"

# Temp directory to store modified markdown files
TEMP_MD_DIR="${OUTPUT_DIR}/temp_md"
mkdir -p "${TEMP_MD_DIR}"

# Function to generate LaTeX footer-includes with filename timestamp
generate_footer_includes() {
  # Escape LaTeX special characters
  escape_latex() {
    echo "$1" | sed -e 's/\\/\\textbackslash /g' \
                    -e 's/{/\\{/g' \
                    -e 's/}/\\}/g' \
                    -e 's/\$/\\\$/g' \
                    -e 's/&/\\&/g' \
                    -e 's/#/\\#/g' \
                    -e 's/_/\\_/g' \
                    -e 's/%/\\%/g' \
                    -e 's/\^/\\^/g' \
                    -e 's/~/{\\textasciitilde}/g'
  }

    # filename=""
    footer_filename=$(escape_latex "$1")
    timestamp=$(date +"%Y-%m-%d %H:%M:%S")
    cat <<EOT > footer-includes.tex
\usepackage{fancyhdr}
\usepackage{lastpage}
\usepackage{datetime}
\usepackage{svg}
\usepackage{graphicx}
\pagestyle{fancy}
\fancyfoot[L]{}
\fancyfoot[C]{}
\fancyfoot[R]{\makebox[0pt][r]{\footnotesize \texttt{$footer_filename} | $timestamp | Page \thepage\ of \pageref{LastPage}}}
\renewcommand{\footrulewidth}{0.4pt}
\setlength{\headheight}{13.6pt}
\setlength{\footskip}{24pt}     % Adjusts the space between text and footer rule
EOT
}

# Function to process markdown file by copying it to a temporary file and replacing .svg references with .pdf references
process_markdown_file() {
    local original_md_file="$1"
    local relative_path="${original_md_file#$BASE_DIR/}"
    local temp_md_file="${TEMP_MD_DIR}/${relative_path}"

    # Create necessary directories for the temp markdown file
    mkdir -p "$(dirname "$temp_md_file")"

    # Make a temporary copy of the original markdown file
    cp "$original_md_file" "$temp_md_file"

    # Find and convert all .svg files referenced in the markdown file to .pdf
    grep -oP '!?\[.*?\]\(\K[^)]*\.svg' "$temp_md_file" | sort -u | while read -r svg_file; do
        pdf_file="${svg_file%.svg}.pdf"
        svg_full_path="$(dirname "$original_md_file")/$svg_file"
        pdf_full_path="$(dirname "$svg_full_path")/$(basename "$pdf_file")"

        if [ -f "$svg_full_path" ]; then
            # Convert the SVG to PDF

            # Variant with Inkscape:
              # inkscape "$svg_full_path" --export-filename="$pdf_full_path"

            # Variant with using rsvg-convert:
              # rsvg-convert -f pdf -o "$pdf_full_path" "$svg_full_path"

            # Variant with  cairosvg:
            python3 -m cairosvg "$svg_full_path" -o "$pdf_full_path"
        else
            echo "Warning: SVG file $svg_full_path not found."
        fi

        # Replace .svg references with absolute path PDF references in the temporary markdown file
        sed -i "s|${svg_file}|${pdf_full_path}|g" "$temp_md_file"
    done

    echo "$temp_md_file"  # Return the path to the temporary markdown file
}

# Find and proceed each .md file
find "$BASE_DIR" -name "*.md" | while read -r md_file; do

    # Get relative path for the .md file
    relative_path="${md_file#$BASE_DIR/}"

    # Create PDF file path
    pdf_file="${OUTPUT_DIR}/${relative_path%.md}.pdf"

    # Echo current md file which is proceeded:
    echo "Create PDF from $relative_path to: $pdf_file"

    # Create directories for the output file
    mkdir -p "$(dirname "$pdf_file")"

    # Extract filename and path for header
    # Old approaches to escape LaTeX special characters:
      # title=$(echo "$relative_path" | sed -e 's/[\/&]/\\&/g' -e 's/_/\\_/g')
      # footer_text=$(echo "$relative_path" | sed -e 's/[{}&%$#_\]/\\&/g' -e 's/_/\\_/g')
    footer_text=$relative_path

    # Generate LaTeX footer-includes file
    generate_footer_includes "$footer_text"

    # Convert SVG to PDF and update markdown references in a temp markdown file
    temp_md_file=$(process_markdown_file "$md_file")


    # Convert .md to .pdf using pandoc

    # Previous variants:
      # pandoc "$md_file" -o "$pdf_file" --toc -V geometry:margin=1in -V fontsize=11pt -V papersize=a4 --pdf-engine=xelatex --include-in-header=header-includes.tex
      # pandoc "$md_file" -o "$pdf_file" --metadata title="$title" --toc -V geometry:margin=1in -V fontsize=11pt -V papersize=a4 --pdf-engine=xelatex --include-in-header=header-includes.tex
      # pandoc "$md_file" -o "$pdf_file" --metadata title="$title" --metadata titleheader="$title" --toc -V geometry:margin=1in -V fontsize=11pt -V papersize=a4 --pdf-engine=xelatex --include-in-header=header-includes.tex
      # pandoc "$md_file" -o "$pdf_file" --metadata title="$title" --toc --template="$OUTPUT_DIR"/latex-template.tex --pdf-engine=xelatex
      # pandoc "$md_file" -o "$pdf_file" --toc -V geometry:margin=1in -V fontsize=11pt -V papersize=a4 --pdf-engine=xelatex --include-in-header=footer-includes.tex
    pandoc "$temp_md_file" -o "$pdf_file" --toc -V geometry:margin=1in -V fontsize=11pt -V papersize=a4 --pdf-engine=xelatex --include-in-header=footer-includes.tex  # --verbose
done

# Clean up header-includes.tex file
rm -f footer-includes.tex
# Remove temporary directory/files
rm -rf "$TEMP_MD_DIR"

# Combine all individual PDFs into a single PDF using ghostscript
final_pdf="${OUTPUT_DIR}/all_created_pdfs_in_one.pdf"

echo "Merge all PDFs into one: $final_pdf"

# Delete the previous created final_pdf
rm -f "$final_pdf"

gs -dBATCH -dNOPAUSE -q -sDEVICE=pdfwrite -sOutputFile="$final_pdf" $(find "$OUTPUT_DIR" -name "*.pdf" | sort)

echo "DONE: Individual PDFs and merged PDF created"