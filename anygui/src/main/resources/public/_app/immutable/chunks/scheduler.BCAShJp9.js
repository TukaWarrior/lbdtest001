function N(){}function D(t,e){for(const n in e)t[n]=e[n];return t}function T(t){return t()}function X(){return Object.create(null)}function B(t){t.forEach(T)}function M(t){return typeof t=="function"}function Y(t,e){return t!=t?e==e:t!==e||t&&typeof t=="object"||typeof t=="function"}let m;function Z(t,e){return t===e?!0:(m||(m=document.createElement("a")),m.href=e,t===m.href)}function $(t,e){return t!=t?e==e:t!==e}function tt(t){return Object.keys(t).length===0}function A(t,...e){if(t==null){for(const r of e)r(void 0);return N}const n=t.subscribe(...e);return n.unsubscribe?()=>n.unsubscribe():n}function et(t){let e;return A(t,n=>e=n)(),e}function nt(t,e,n){t.$$.on_destroy.push(A(e,n))}function rt(t,e,n,r){if(t){const i=j(t,e,n,r);return t[0](i)}}function j(t,e,n,r){return t[1]&&r?D(n.ctx.slice(),t[1](r(e))):n.ctx}function it(t,e,n,r){if(t[2]&&r){const i=t[2](r(n));if(e.dirty===void 0)return i;if(typeof i=="object"){const u=[],c=Math.max(e.dirty.length,i.length);for(let s=0;s<c;s+=1)u[s]=e.dirty[s]|i[s];return u}return e.dirty|i}return e.dirty}function ct(t,e,n,r,i,u){if(i){const c=j(e,n,r,u);t.p(c,i)}}function lt(t){if(t.ctx.length>32){const e=[],n=t.ctx.length/32;for(let r=0;r<n;r++)e[r]=-1;return e}return-1}function st(t){const e={};for(const n in t)n[0]!=="$"&&(e[n]=t[n]);return e}function ut(t,e){const n={};e=new Set(e);for(const r in t)!e.has(r)&&r[0]!=="$"&&(n[r]=t[r]);return n}function ot(t){return t&&M(t.destroy)?t.destroy:N}let y=!1;function at(){y=!0}function ft(){y=!1}function H(t,e,n,r){for(;t<e;){const i=t+(e-t>>1);n(i)<=r?t=i+1:e=i}return t}function I(t){if(t.hydrate_init)return;t.hydrate_init=!0;let e=t.childNodes;if(t.nodeName==="HEAD"){const l=[];for(let o=0;o<e.length;o++){const a=e[o];a.claim_order!==void 0&&l.push(a)}e=l}const n=new Int32Array(e.length+1),r=new Int32Array(e.length);n[0]=-1;let i=0;for(let l=0;l<e.length;l++){const o=e[l].claim_order,a=(i>0&&e[n[i]].claim_order<=o?i+1:H(1,i,P=>e[n[P]].claim_order,o))-1;r[l]=n[a]+1;const k=a+1;n[k]=l,i=Math.max(k,i)}const u=[],c=[];let s=e.length-1;for(let l=n[i]+1;l!=0;l=r[l-1]){for(u.push(e[l-1]);s>=l;s--)c.push(e[s]);s--}for(;s>=0;s--)c.push(e[s]);u.reverse(),c.sort((l,o)=>l.claim_order-o.claim_order);for(let l=0,o=0;l<c.length;l++){for(;o<u.length&&c[l].claim_order>=u[o].claim_order;)o++;const a=o<u.length?u[o]:null;t.insertBefore(c[l],a)}}function L(t,e){if(y){for(I(t),(t.actual_end_child===void 0||t.actual_end_child!==null&&t.actual_end_child.parentNode!==t)&&(t.actual_end_child=t.firstChild);t.actual_end_child!==null&&t.actual_end_child.claim_order===void 0;)t.actual_end_child=t.actual_end_child.nextSibling;e!==t.actual_end_child?(e.claim_order!==void 0||e.parentNode!==t)&&t.insertBefore(e,t.actual_end_child):t.actual_end_child=e.nextSibling}else(e.parentNode!==t||e.nextSibling!==null)&&t.appendChild(e)}function _t(t,e,n){y&&!n?L(t,e):(e.parentNode!==t||e.nextSibling!=n)&&t.insertBefore(e,n||null)}function dt(t){t.parentNode&&t.parentNode.removeChild(t)}function ht(t,e){for(let n=0;n<t.length;n+=1)t[n]&&t[n].d(e)}function z(t){return document.createElement(t)}function F(t){return document.createElementNS("http://www.w3.org/2000/svg",t)}function w(t){return document.createTextNode(t)}function mt(){return w(" ")}function pt(){return w("")}function yt(t,e,n,r){return t.addEventListener(e,n,r),()=>t.removeEventListener(e,n,r)}function S(t,e,n){n==null?t.removeAttribute(e):t.getAttribute(e)!==n&&t.setAttribute(e,n)}const U=["width","height"];function bt(t,e){const n=Object.getOwnPropertyDescriptors(t.__proto__);for(const r in e)e[r]==null?t.removeAttribute(r):r==="style"?t.style.cssText=e[r]:r==="__value"?t.value=t[r]=e[r]:n[r]&&n[r].set&&U.indexOf(r)===-1?t[r]=e[r]:S(t,r,e[r])}function gt(t,e){for(const n in e)S(t,n,e[n])}function xt(t){return t.dataset.svelteH}function vt(t){return Array.from(t.childNodes)}function W(t){t.claim_info===void 0&&(t.claim_info={last_index:0,total_claimed:0})}function C(t,e,n,r,i=!1){W(t);const u=(()=>{for(let c=t.claim_info.last_index;c<t.length;c++){const s=t[c];if(e(s)){const l=n(s);return l===void 0?t.splice(c,1):t[c]=l,i||(t.claim_info.last_index=c),s}}for(let c=t.claim_info.last_index-1;c>=0;c--){const s=t[c];if(e(s)){const l=n(s);return l===void 0?t.splice(c,1):t[c]=l,i?l===void 0&&t.claim_info.last_index--:t.claim_info.last_index=c,s}}return r()})();return u.claim_order=t.claim_info.total_claimed,t.claim_info.total_claimed+=1,u}function O(t,e,n,r){return C(t,i=>i.nodeName===e,i=>{const u=[];for(let c=0;c<i.attributes.length;c++){const s=i.attributes[c];n[s.name]||u.push(s.name)}u.forEach(c=>i.removeAttribute(c))},()=>r(e))}function wt(t,e,n){return O(t,e,n,z)}function kt(t,e,n){return O(t,e,n,F)}function G(t,e){return C(t,n=>n.nodeType===3,n=>{const r=""+e;if(n.data.startsWith(r)){if(n.data.length!==r.length)return n.splitText(r.length)}else n.data=r},()=>w(e),!0)}function Et(t){return G(t," ")}function Nt(t,e){e=""+e,t.data!==e&&(t.data=e)}function At(t,e){t.value=e??""}function jt(t,e,n,r){n==null?t.style.removeProperty(e):t.style.setProperty(e,n,"")}function J(t,e,{bubbles:n=!1,cancelable:r=!1}={}){return new CustomEvent(t,{detail:e,bubbles:n,cancelable:r})}function St(t,e){return new t(e)}let p;function b(t){p=t}function d(){if(!p)throw new Error("Function called outside component initialization");return p}function Ct(t){d().$$.on_mount.push(t)}function Ot(t){d().$$.after_update.push(t)}function qt(t){d().$$.on_destroy.push(t)}function Pt(){const t=d();return(e,n,{cancelable:r=!1}={})=>{const i=t.$$.callbacks[e];if(i){const u=J(e,n,{cancelable:r});return i.slice().forEach(c=>{c.call(t,u)}),!u.defaultPrevented}return!0}}function Dt(t,e){return d().$$.context.set(t,e),e}function Tt(t){return d().$$.context.get(t)}function Bt(t,e){const n=t.$$.callbacks[e.type];n&&n.slice().forEach(r=>r.call(this,e))}const h=[],E=[];let _=[];const x=[],q=Promise.resolve();let v=!1;function K(){v||(v=!0,q.then(R))}function Mt(){return K(),q}function Q(t){_.push(t)}function Ht(t){x.push(t)}const g=new Set;let f=0;function R(){if(f!==0)return;const t=p;do{try{for(;f<h.length;){const e=h[f];f++,b(e),V(e.$$)}}catch(e){throw h.length=0,f=0,e}for(b(null),h.length=0,f=0;E.length;)E.pop()();for(let e=0;e<_.length;e+=1){const n=_[e];g.has(n)||(g.add(n),n())}_.length=0}while(h.length);for(;x.length;)x.pop()();v=!1,g.clear(),b(t)}function V(t){if(t.fragment!==null){t.update(),B(t.before_update);const e=t.dirty;t.dirty=[-1],t.fragment&&t.fragment.p(t.ctx,e),t.after_update.forEach(Q)}}function It(t){const e=[],n=[];_.forEach(r=>t.indexOf(r)===-1?e.push(r):n.push(r)),n.forEach(r=>r()),_=e}export{Pt as $,R as A,tt as B,Q as C,It as D,p as E,b as F,T as G,h as H,K as I,at as J,ft as K,et as L,F as M,kt as N,$ as O,yt as P,D as Q,st as R,rt as S,ct as T,lt as U,it as V,ut as W,qt as X,ht as Y,bt as Z,Z as _,Y as a,Dt as a0,Tt as a1,ot as a2,gt as a3,Ht as a4,Bt as a5,At as a6,xt as a7,mt as b,wt as c,vt as d,z as e,G as f,dt as g,Et as h,M as i,_t as j,L as k,Nt as l,nt as m,N as n,pt as o,Ot as p,Ct as q,B as r,A as s,w as t,S as u,jt as v,E as w,St as x,Mt as y,X as z};
