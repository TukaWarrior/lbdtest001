import{t as P,a as A}from"./index.D-I4eV3u.js";import{r as C,L as E}from"./scheduler.BCAShJp9.js";import{w as D}from"./index.CC5YZL8Q.js";function G(t){return(t==null?void 0:t.length)!==void 0?t:Array.from(t)}function J(t,o){P(t,1,1,()=>{o.delete(t.key)})}function K(t,o,p,u,m,b,f,_,c,j,T,S){let g=t.length,k=b.length,h=g;const $={};for(;h--;)$[t[h].key]=h;const I=[],n=new Map,e=new Map,s=[];for(h=k;h--;){const i=S(m,b,h),y=p(i);let l=f.get(y);l?s.push(()=>l.p(i,o)):(l=j(y,i),l.c()),n.set(y,I[h]=l),y in $&&e.set(y,Math.abs(h-$[y]))}const d=new Set,a=new Set;function r(i){A(i,1),i.m(_,T),f.set(i.key,i),T=i.first,k--}for(;g&&k;){const i=I[k-1],y=t[g-1],l=i.key,x=y.key;i===y?(T=i.first,g--,k--):n.has(x)?!f.has(l)||d.has(l)?r(i):a.has(x)?g--:e.get(l)>e.get(x)?(a.add(l),r(i)):(d.add(x),g--):(c(y,f),g--)}for(;g--;){const i=t[g];n.has(i.key)||c(i,f)}for(;k;)r(I[k-1]);return C(s),I}function N(t,o){const p={},u={},m={$$scope:1};let b=t.length;for(;b--;){const f=t[b],_=o[b];if(_){for(const c in f)c in _||(u[c]=1);for(const c in _)m[c]||(p[c]=_[c],m[c]=1);t[b]=_}else for(const c in f)m[c]=1}for(const f in u)f in p||(p[f]=void 0);return p}function Q(t){return typeof t=="object"&&t!==null?t:{}}function R(...t){return t.filter(Boolean).join(" ")}const H=typeof document<"u";function M(t){const o=D(t);function p(m){H&&o.set(m)}function u(m){H&&o.update(m)}return{subscribe:o.subscribe,set:p,update:u}}let B=0;function F(){const t=M([]),o=M([]);function p(n){t.update(e=>[n,...e])}function u(n){var l;const{message:e,...s}=n,d=typeof(n==null?void 0:n.id)=="number"||n.id&&((l=n.id)==null?void 0:l.length)>0?n.id:B++,a=n.dismissable===void 0?!0:n.dismissable,r=n.type===void 0?"default":n.type;return E(t).find(x=>x.id===d)?t.update(x=>x.map(v=>v.id===d?{...v,...n,id:d,title:e,dismissable:a,type:r,updated:!0}:{...v,updated:!1})):p({...s,id:d,title:e,dismissable:a,type:r}),d}function m(n){if(n===void 0){t.update(e=>e.map(s=>({...s,dismiss:!0})));return}return t.update(e=>e.map(s=>s.id===n?{...s,dismiss:!0}:s)),n}function b(n){if(n===void 0){t.set([]);return}return t.update(e=>e.filter(s=>s.id!==n)),n}function f(n,e){return u({...e,type:"default",message:n})}function _(n,e){return u({...e,type:"error",message:n})}function c(n,e){return u({...e,type:"success",message:n})}function j(n,e){return u({...e,type:"info",message:n})}function T(n,e){return u({...e,type:"warning",message:n})}function S(n,e){return u({...e,type:"loading",message:n})}function g(n,e){if(!e)return;let s;e.loading!==void 0&&(s=u({...e,promise:n,type:"loading",message:e.loading}));const d=n instanceof Promise?n:n();let a=s!==void 0;return d.then(r=>{if(r&&typeof r.ok=="boolean"&&!r.ok){a=!1;const i=typeof e.error=="function"?e.error(`HTTP error! status: ${r.status}`):e.error;u({id:s,type:"error",message:i})}else if(e.success!==void 0){a=!1;const i=typeof e.success=="function"?e.success(r):e.success;u({id:s,type:"success",message:i})}}).catch(r=>{if(e.error!==void 0){a=!1;const i=typeof e.error=="function"?e.error(r):e.error;u({id:s,type:"error",message:i})}}).finally(()=>{var r;a&&(m(s),s=void 0),(r=e.finally)==null||r.call(e)}),s}function k(n,e){const s=(e==null?void 0:e.id)||B++;return u({component:n,id:s,...e}),s}function h(n){o.update(e=>e.filter(s=>s.toastId!==n))}function $(n){if(E(o).find(s=>s.toastId===n.toastId)===void 0){o.update(s=>[n,...s]);return}o.update(s=>s.map(d=>d.toastId===n.toastId?n:d))}function I(){t.set([]),o.set([])}return{create:u,addToast:p,dismiss:m,remove:b,message:f,error:_,success:c,info:j,warning:T,loading:S,promise:g,custom:k,removeHeight:h,setHeight:$,reset:I,toasts:t,heights:o}}const w=F();function L(t,o){return w.create({message:t,...o})}const O=L,U=Object.assign(O,{success:w.success,info:w.info,warning:w.warning,error:w.error,custom:w.custom,message:w.message,promise:w.promise,dismiss:w.dismiss,loading:w.loading}),V=t=>({subscribe:t});export{Q as a,K as b,R as c,U as d,G as e,N as g,J as o,w as t,V as u};