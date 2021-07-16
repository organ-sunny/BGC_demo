!function(t,e){"object"==typeof exports&&"object"==typeof module?module.exports=e():"function"==typeof define&&define.amd?define([],e):"object"==typeof exports?exports.zj0724common=e():t.zj0724common=e()}(window,(function(){return function(t){var e={};function r(i){if(e[i])return e[i].exports;var n=e[i]={i:i,l:!1,exports:{}};return t[i].call(n.exports,n,n.exports,r),n.l=!0,n.exports}return r.m=t,r.c=e,r.d=function(t,e,i){r.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:i})},r.r=function(t){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},r.t=function(t,e){if(1&e&&(t=r(t)),8&e)return t;if(4&e&&"object"==typeof t&&t&&t.__esModule)return t;var i=Object.create(null);if(r.r(i),Object.defineProperty(i,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var n in t)r.d(i,n,function(e){return t[e]}.bind(null,n));return i},r.n=function(t){var e=t&&t.__esModule?function(){return t.default}:function(){return t};return r.d(e,"a",e),e},r.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},r.p="",r(r.s=0)}([function(t,e,r){"use strict";r.r(e),r.d(e,"variable",(function(){return i})),r.d(e,"http",(function(){return l})),r.d(e,"base64",(function(){return o}));var i={isNull:t=>null==t,isEmpty(t){if(this.isNull(t))return!0;let e=this.getType(t);return e===String&&""===t||(e===Array&&0===t.length||e===Object&&0===Object.keys(t).length)},getType(t){return this.isNull(t)?null:t.constructor},isObject(t){if(this.isNull(t))return!1;if(t instanceof Object){let e=this.getType(t);return 0===e.toString().indexOf("class")||(e===Array||e===Object)}return!1},clone(t){if(this.isObject(t)){if(this.getType(t)===Array){let e=[];for(let r of t)e.push(this.clone(r));return e}{let e={};for(let r in t)if(t.hasOwnProperty(r)){let i=t[r];this.isObject(i)?e[r]=this.clone(i):e[r]=null}return this.assignment(e,t),e}}return this.isNull(t)?null:t},assignment(t,e,r){let i=this.getType(t);if(this.isObject(t)&&this.isObject(e))if(i===Array){let n=!0;if(!0===r&&this.getType(e)!==Array&&(n=!1),i!==this.getType(e)&&(n=!1),n){t.splice(0,t.length);for(let r of e)t.push(this.clone(r))}}else for(let i in t)if(t.hasOwnProperty(i)){let n=t[i],s=e[i];if(void 0===s)continue;this.isObject(n)?this.getType(n)===this.getType(s)?this.assignment(n,s):!this.isNull(r)&&this.getType(r)===Boolean&&r||(t[i]=this.clone(s)):!this.isNull(r)&&this.getType(r)===Boolean&&r?(this.isNull(n)||this.getType(n)===this.getType(s))&&(t[i]=this.clone(s)):t[i]=this.clone(s)}},toStr(t){return this.isNull(t)?null:this.isObject(t)?JSON.stringify(t):t.toString()},clean(t){if(this.isObject(t))for(let e in t)if(t.hasOwnProperty(e)){let r=t[e];this.getType(r)===Array?t[e]=[]:this.isObject(r)?this.clean(r):t[e]=null}}};class n extends Error{constructor(t){super(t)}}let s=[String,Number,Object,Array,Boolean];class a{constructor(t){if(this.url=null,this.type=null,this.header=null,this.urlData=null,this.data=null,this.check={urlData:null,data:null},i.assignment(this,t),i.isEmpty(this.url))throw new n("url不能为空");if(i.isEmpty(this.type))throw new n("type不能为空");if("get"===this.type&&(this.type="GET"),"post"===this.type&&(this.type="POST"),"put"===this.type&&(this.type="PUT"),"delete"===this.type&&(this.type="DELETE"),"POST"!==this.type&&"GET"!==this.type&&"PUT"!==this.type&&"DELETE"!==this.type)throw new n("type错误");if(!i.isNull(this.header)){if(!i.isObject(this.header))throw new n("header错误");for(let t in this.header)this.header.hasOwnProperty(t)&&(this.header[t]=i.toStr(this.header[t]))}if(!i.isNull(this.urlData)&&!i.isObject(this.urlData))throw new n("urlData错误");if(!i.isNull(this.check.urlData)){if(!a.withType(this.check.urlData))throw new n("check.urlData错误");let t=a.getNewData(this.check.urlData);i.assignment(t,this.urlData,!0),this.urlData=t}if(!i.isNull(this.check.data)){if(!a.withType(this.check.data))throw new n("check.data错误");let t=a.getNewData(this.check.data);i.assignment(t,this.data),this.data=t}if(!i.isNull(this.urlData)){let t="?";for(let e in this.url)if(this.url.hasOwnProperty(e)){let r=this.url[e];i.isNull(r)||(r=i.toStr(r),t=t+e+"="+r+"&")}"?"!==t&&(t=""),this.url=this.url+t}if(null!==this.data){let t=!1;for(let e in this.data)if(this.data.hasOwnProperty(e)&&i.getType(this.type)===File){t=!0;break}if(t){let t=new FormData;for(let e in this.data)this.data.hasOwnProperty(e)&&t.append(e,this.data[e]);this.data=t}else this.data=i.toStr(this.data)}}static withType(t){if(!i.isObject(t))return!1;for(let e in t)if(t.hasOwnProperty(e)){let r=t[e];if(i.isObject(r)){if(!a.withType(r))return!1}else{let t=!1;for(let e of s)e===r&&(t=!0);if(!t)return!1}}return!0}static getNewData(t){let e={};for(let r in t)if(t.hasOwnProperty(r)){let n=t[r];i.isObject(n)?e[r]=a.getNewData(n):e[r]=null}return e}}var l={send:t=>new Promise(e=>{let r=new a(t),n=new XMLHttpRequest;if(n.open(r.type,r.url),!i.isNull(r.header))for(let t in r.header)r.header.hasOwnProperty(t)&&n.setRequestHeader(t,r.header[t]);i.isNull(r.data)?n.send():n.send(r.data),n.onreadystatechange=function(){200===n.status&&4===n.readyState&&e(n.response)}})};let h="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";var o={encode(t){let e,r,i,n,s,a,l,o="",u=0;for(t=function(t){let e="";t=t.replace(/\r\n/g,"\n");for(let r=0;r<t.length;r++){let i=t.charCodeAt(r);i<128?e+=String.fromCharCode(i):i>127&&i<2048?(e+=String.fromCharCode(i>>6|192),e+=String.fromCharCode(63&i|128)):(e+=String.fromCharCode(i>>12|224),e+=String.fromCharCode(i>>6&63|128),e+=String.fromCharCode(63&i|128))}return e}(t);u<t.length;)e=t.charCodeAt(u++),r=t.charCodeAt(u++),i=t.charCodeAt(u++),n=e>>2,s=(3&e)<<4|r>>4,a=(15&r)<<2|i>>6,l=63&i,isNaN(r)?a=l=64:isNaN(i)&&(l=64),o=o+h.charAt(n)+h.charAt(s)+h.charAt(a)+h.charAt(l);return o},decode(t){let e,r,i,n,s,a,l,o="",u=0;for(t=t.replace(/[^A-Za-z0-9\+\/\=]/g,"");u<t.length;)n=h.indexOf(t.charAt(u++)),s=h.indexOf(t.charAt(u++)),a=h.indexOf(t.charAt(u++)),l=h.indexOf(t.charAt(u++)),e=n<<2|s>>4,r=(15&s)<<4|a>>2,i=(3&a)<<6|l,o+=String.fromCharCode(e),64!==a&&(o+=String.fromCharCode(r)),64!==l&&(o+=String.fromCharCode(i));return o=function(t){let e,r,i="",n=0,s=0;for(;n<t.length;)e=t.charCodeAt(n),e<128?(i+=String.fromCharCode(e),n++):e>191&&e<224?(r=t.charCodeAt(n+1),i+=String.fromCharCode((31&e)<<6|63&r),n+=2):(r=t.charCodeAt(n+1),s=t.charCodeAt(n+2),i+=String.fromCharCode((15&e)<<12|(63&r)<<6|63&s),n+=3);return i}(o),o}}}])}));