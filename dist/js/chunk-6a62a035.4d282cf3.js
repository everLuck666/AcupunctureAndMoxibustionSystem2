(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6a62a035"],{"1da1":function(t,e,r){"use strict";r.d(e,"a",(function(){return o}));r("d3b7");function n(t,e,r,n,o,a,i){try{var c=t[a](i),u=c.value}catch(s){return void r(s)}c.done?e(u):Promise.resolve(u).then(n,o)}function o(t){return function(){var e=this,r=arguments;return new Promise((function(o,a){var i=t.apply(e,r);function c(t){n(i,o,a,c,u,"next",t)}function u(t){n(i,o,a,c,u,"throw",t)}c(void 0)}))}}},"1dde":function(t,e,r){var n=r("d039"),o=r("b622"),a=r("2d00"),i=o("species");t.exports=function(t){return a>=51||!n((function(){var e=[],r=e.constructor={};return r[i]=function(){return{foo:1}},1!==e[t](Boolean).foo}))}},"65f0":function(t,e,r){var n=r("861d"),o=r("e8b5"),a=r("b622"),i=a("species");t.exports=function(t,e){var r;return o(t)&&(r=t.constructor,"function"!=typeof r||r!==Array&&!o(r.prototype)?n(r)&&(r=r[i],null===r&&(r=void 0)):r=void 0),new(void 0===r?Array:r)(0===e?0:e)}},8418:function(t,e,r){"use strict";var n=r("c04e"),o=r("9bf2"),a=r("5c6c");t.exports=function(t,e,r){var i=n(e);i in t?o.f(t,i,a(0,r)):t[i]=r}},"871d":function(t,e,r){"use strict";r.r(e);var n=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}]},[r("div",{staticClass:"goods_header"},[r("span",[t._v("商品管理")]),r("button",{on:{click:function(e){return t.addGoods()}}},[t._v("添加商品")])]),r("el-table",{staticStyle:{width:"98%"},attrs:{data:t.goodsData,border:""}},[r("el-table-column",{attrs:{prop:"productName",label:"商品名称",align:"center"}}),r("el-table-column",{attrs:{prop:"describe",label:"商品描述",align:"center"}}),r("el-table-column",{attrs:{prop:"productPrice",label:"商品价格",align:"center"}}),r("el-table-column",{attrs:{prop:"sales",label:"商品总销量",align:"center"}}),r("el-table-column",{attrs:{prop:"imageUrl",label:"商品图片",align:"center"}}),r("el-table-column",{attrs:{label:"上架状态",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row?r("el-switch",{staticClass:"captcha-img",attrs:{"active-color":"#13ce66","inactive-color":"#ECECEC","active-value":"1","inactive-value":"0"},on:{change:function(r){return t.statusChange(e.row.status,e.$index,e.row.productName)}},model:{value:e.row.status,callback:function(r){t.$set(e.row,"status",r)},expression:"scope.row.status"}}):t._e()]}}])}),r("el-table-column",{attrs:{label:"操作",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[r("el-button",{attrs:{type:"danger",icon:"el-icon-delete",size:"small"},on:{click:function(r){return t.deleteGood(e.row.productName)}}})]}}])})],1)],1)},o=[],a=(r("a434"),r("96cf"),r("1da1")),i={created:function(){this.getGoods()},data:function(){return{goodsData:[],loading:!1}},methods:{deleteGood:function(t){var e=this;return Object(a["a"])(regeneratorRuntime.mark((function r(){var n;return regeneratorRuntime.wrap((function(r){while(1)switch(r.prev=r.next){case 0:n="删除后将无法恢复，请确认是否删除",e.$confirm(n,"删除确认",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning",callback:function(){var r=Object(a["a"])(regeneratorRuntime.mark((function r(n){return regeneratorRuntime.wrap((function(r){while(1)switch(r.prev=r.next){case 0:if("confirm"!=n){r.next=5;break}return r.next=3,e.$http.delete("/goods/goods?goodsName="+t);case 3:r.sent,e.getGoods();case 5:case"end":return r.stop()}}),r)})));function n(t){return r.apply(this,arguments)}return n}()});case 2:case"end":return r.stop()}}),r)})))()},getGoods:function(){var t=this;return Object(a["a"])(regeneratorRuntime.mark((function e(){var r,n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return t.loading=!0,e.next=3,t.$http.get("/goods/goods");case 3:if(r=e.sent,"token过期"!=r.data.msg&&"这个账号在别的地方登陆，请重新登录"!=r.data.msg){e.next=6;break}return e.abrupt("return",t.$message.error("登录状态过期，请重新登录"));case 6:if(200==r.data.status){for(t.goodsData=JSON.parse(JSON.stringify(r.data.data)),n=0;n<t.goodsData.length;n++)t.goodsData[n].sales||(t.goodsData[n].sales=0),t.goodsData[n].productName||t.goodsData.splice(n,1);t.loading=!1}else t.loading=!1;case 7:case"end":return e.stop()}}),e)})))()},statusChange:function(t,e,r){var n=this;return Object(a["a"])(regeneratorRuntime.mark((function o(){var i;return regeneratorRuntime.wrap((function(o){while(1)switch(o.prev=o.next){case 0:"0"===t&&(n.goodsData[e].status="1"),"1"===t&&(n.goodsData[e].status="0"),"1"===t&&(i="上架后用户将正常购买本商品，是否确认上架本商品？"),"0"===t&&(i="下架后用户将正常购买本商品，是否确认下架本商品？"),n.$confirm(i,"提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning",callback:function(){var t=Object(a["a"])(regeneratorRuntime.mark((function t(e){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if("confirm"!=e){t.next=7;break}return n.loading=!0,t.next=4,n.$http.post("/goods/goods?goodsName="+r);case 4:t.sent,n.loading=!1,n.getGoods();case 7:case"end":return t.stop()}}),t)})));function e(e){return t.apply(this,arguments)}return e}()});case 5:case"end":return o.stop()}}),o)})))()},addGoods:function(){this.$router.push("/AddGoods")}}},c=i,u=(r("f1cf"),r("2877")),s=Object(u["a"])(c,n,o,!1,null,"7ea12142",null);e["default"]=s.exports},"8f89":function(t,e,r){},"96cf":function(t,e,r){var n=function(t){"use strict";var e,r=Object.prototype,n=r.hasOwnProperty,o="function"===typeof Symbol?Symbol:{},a=o.iterator||"@@iterator",i=o.asyncIterator||"@@asyncIterator",c=o.toStringTag||"@@toStringTag";function u(t,e,r){return Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}),t[e]}try{u({},"")}catch(C){u=function(t,e,r){return t[e]=r}}function s(t,e,r,n){var o=e&&e.prototype instanceof v?e:v,a=Object.create(o.prototype),i=new G(n||[]);return a._invoke=O(t,r,i),a}function l(t,e,r){try{return{type:"normal",arg:t.call(e,r)}}catch(C){return{type:"throw",arg:C}}}t.wrap=s;var f="suspendedStart",h="suspendedYield",d="executing",p="completed",g={};function v(){}function y(){}function m(){}var w={};w[a]=function(){return this};var b=Object.getPrototypeOf,x=b&&b(b(N([])));x&&x!==r&&n.call(x,a)&&(w=x);var E=m.prototype=v.prototype=Object.create(w);function L(t){["next","throw","return"].forEach((function(e){u(t,e,(function(t){return this._invoke(e,t)}))}))}function k(t,e){function r(o,a,i,c){var u=l(t[o],t,a);if("throw"!==u.type){var s=u.arg,f=s.value;return f&&"object"===typeof f&&n.call(f,"__await")?e.resolve(f.__await).then((function(t){r("next",t,i,c)}),(function(t){r("throw",t,i,c)})):e.resolve(f).then((function(t){s.value=t,i(s)}),(function(t){return r("throw",t,i,c)}))}c(u.arg)}var o;function a(t,n){function a(){return new e((function(e,o){r(t,n,e,o)}))}return o=o?o.then(a,a):a()}this._invoke=a}function O(t,e,r){var n=f;return function(o,a){if(n===d)throw new Error("Generator is already running");if(n===p){if("throw"===o)throw a;return R()}r.method=o,r.arg=a;while(1){var i=r.delegate;if(i){var c=_(i,r);if(c){if(c===g)continue;return c}}if("next"===r.method)r.sent=r._sent=r.arg;else if("throw"===r.method){if(n===f)throw n=p,r.arg;r.dispatchException(r.arg)}else"return"===r.method&&r.abrupt("return",r.arg);n=d;var u=l(t,e,r);if("normal"===u.type){if(n=r.done?p:h,u.arg===g)continue;return{value:u.arg,done:r.done}}"throw"===u.type&&(n=p,r.method="throw",r.arg=u.arg)}}}function _(t,r){var n=t.iterator[r.method];if(n===e){if(r.delegate=null,"throw"===r.method){if(t.iterator["return"]&&(r.method="return",r.arg=e,_(t,r),"throw"===r.method))return g;r.method="throw",r.arg=new TypeError("The iterator does not provide a 'throw' method")}return g}var o=l(n,t.iterator,r.arg);if("throw"===o.type)return r.method="throw",r.arg=o.arg,r.delegate=null,g;var a=o.arg;return a?a.done?(r[t.resultName]=a.value,r.next=t.nextLoc,"return"!==r.method&&(r.method="next",r.arg=e),r.delegate=null,g):a:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,g)}function S(t){var e={tryLoc:t[0]};1 in t&&(e.catchLoc=t[1]),2 in t&&(e.finallyLoc=t[2],e.afterLoc=t[3]),this.tryEntries.push(e)}function j(t){var e=t.completion||{};e.type="normal",delete e.arg,t.completion=e}function G(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(S,this),this.reset(!0)}function N(t){if(t){var r=t[a];if(r)return r.call(t);if("function"===typeof t.next)return t;if(!isNaN(t.length)){var o=-1,i=function r(){while(++o<t.length)if(n.call(t,o))return r.value=t[o],r.done=!1,r;return r.value=e,r.done=!0,r};return i.next=i}}return{next:R}}function R(){return{value:e,done:!0}}return y.prototype=E.constructor=m,m.constructor=y,y.displayName=u(m,c,"GeneratorFunction"),t.isGeneratorFunction=function(t){var e="function"===typeof t&&t.constructor;return!!e&&(e===y||"GeneratorFunction"===(e.displayName||e.name))},t.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,m):(t.__proto__=m,u(t,c,"GeneratorFunction")),t.prototype=Object.create(E),t},t.awrap=function(t){return{__await:t}},L(k.prototype),k.prototype[i]=function(){return this},t.AsyncIterator=k,t.async=function(e,r,n,o,a){void 0===a&&(a=Promise);var i=new k(s(e,r,n,o),a);return t.isGeneratorFunction(r)?i:i.next().then((function(t){return t.done?t.value:i.next()}))},L(E),u(E,c,"Generator"),E[a]=function(){return this},E.toString=function(){return"[object Generator]"},t.keys=function(t){var e=[];for(var r in t)e.push(r);return e.reverse(),function r(){while(e.length){var n=e.pop();if(n in t)return r.value=n,r.done=!1,r}return r.done=!0,r}},t.values=N,G.prototype={constructor:G,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(j),!t)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=e)},stop:function(){this.done=!0;var t=this.tryEntries[0],e=t.completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var r=this;function o(n,o){return c.type="throw",c.arg=t,r.next=n,o&&(r.method="next",r.arg=e),!!o}for(var a=this.tryEntries.length-1;a>=0;--a){var i=this.tryEntries[a],c=i.completion;if("root"===i.tryLoc)return o("end");if(i.tryLoc<=this.prev){var u=n.call(i,"catchLoc"),s=n.call(i,"finallyLoc");if(u&&s){if(this.prev<i.catchLoc)return o(i.catchLoc,!0);if(this.prev<i.finallyLoc)return o(i.finallyLoc)}else if(u){if(this.prev<i.catchLoc)return o(i.catchLoc,!0)}else{if(!s)throw new Error("try statement without catch or finally");if(this.prev<i.finallyLoc)return o(i.finallyLoc)}}}},abrupt:function(t,e){for(var r=this.tryEntries.length-1;r>=0;--r){var o=this.tryEntries[r];if(o.tryLoc<=this.prev&&n.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var a=o;break}}a&&("break"===t||"continue"===t)&&a.tryLoc<=e&&e<=a.finallyLoc&&(a=null);var i=a?a.completion:{};return i.type=t,i.arg=e,a?(this.method="next",this.next=a.finallyLoc,g):this.complete(i)},complete:function(t,e){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&e&&(this.next=e),g},finish:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.finallyLoc===t)return this.complete(r.completion,r.afterLoc),j(r),g}},catch:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.tryLoc===t){var n=r.completion;if("throw"===n.type){var o=n.arg;j(r)}return o}}throw new Error("illegal catch attempt")},delegateYield:function(t,r,n){return this.delegate={iterator:N(t),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=e),g}},t}(t.exports);try{regeneratorRuntime=n}catch(o){Function("r","regeneratorRuntime = r")(n)}},a434:function(t,e,r){"use strict";var n=r("23e7"),o=r("23cb"),a=r("a691"),i=r("50c4"),c=r("7b0b"),u=r("65f0"),s=r("8418"),l=r("1dde"),f=r("ae40"),h=l("splice"),d=f("splice",{ACCESSORS:!0,0:0,1:2}),p=Math.max,g=Math.min,v=9007199254740991,y="Maximum allowed length exceeded";n({target:"Array",proto:!0,forced:!h||!d},{splice:function(t,e){var r,n,l,f,h,d,m=c(this),w=i(m.length),b=o(t,w),x=arguments.length;if(0===x?r=n=0:1===x?(r=0,n=w-b):(r=x-2,n=g(p(a(e),0),w-b)),w+r-n>v)throw TypeError(y);for(l=u(m,n),f=0;f<n;f++)h=b+f,h in m&&s(l,f,m[h]);if(l.length=n,r<n){for(f=b;f<w-n;f++)h=f+n,d=f+r,h in m?m[d]=m[h]:delete m[d];for(f=w;f>w-n+r;f--)delete m[f-1]}else if(r>n)for(f=w-n;f>b;f--)h=f+n-1,d=f+r-1,h in m?m[d]=m[h]:delete m[d];for(f=0;f<r;f++)m[f+b]=arguments[f+2];return m.length=w-n+r,l}})},ae40:function(t,e,r){var n=r("83ab"),o=r("d039"),a=r("5135"),i=Object.defineProperty,c={},u=function(t){throw t};t.exports=function(t,e){if(a(c,t))return c[t];e||(e={});var r=[][t],s=!!a(e,"ACCESSORS")&&e.ACCESSORS,l=a(e,0)?e[0]:u,f=a(e,1)?e[1]:void 0;return c[t]=!!r&&!o((function(){if(s&&!n)return!0;var t={length:-1};s?i(t,1,{enumerable:!0,get:u}):t[1]=1,r.call(t,l,f)}))}},e8b5:function(t,e,r){var n=r("c6b6");t.exports=Array.isArray||function(t){return"Array"==n(t)}},f1cf:function(t,e,r){"use strict";r("8f89")}}]);