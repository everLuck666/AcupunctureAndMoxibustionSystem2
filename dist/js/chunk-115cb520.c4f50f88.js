(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-115cb520"],{"1da1":function(t,e,r){"use strict";r.d(e,"a",(function(){return o}));r("d3b7");function n(t,e,r,n,o,i,a){try{var c=t[i](a),u=c.value}catch(s){return void r(s)}c.done?e(u):Promise.resolve(u).then(n,o)}function o(t){return function(){var e=this,r=arguments;return new Promise((function(o,i){var a=t.apply(e,r);function c(t){n(a,o,i,c,u,"next",t)}function u(t){n(a,o,i,c,u,"throw",t)}c(void 0)}))}}},"3a03":function(t,e,r){"use strict";r("68b9")},"3f0b":function(t,e,r){"use strict";r.r(e);var n=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}]},[r("el-dialog",{attrs:{title:"上传视频",visible:t.visable,width:"500px"},on:{"update:visible":function(e){t.visable=e}}},[r("el-form",{attrs:{model:t.video}},[r("el-form-item",[r("el-input",{attrs:{placeholder:"视频名称"},model:{value:t.video.name,callback:function(e){t.$set(t.video,"name",e)},expression:"video.name"}})],1),r("el-form-item",[r("el-upload",{staticClass:"avatar-uploader",attrs:{action:"","show-file-list":!1,"before-upload":t.beforeAvatarUpload}},[t.videoUrl?r("video",{staticClass:"avatar",attrs:{src:t.videoUrl,id:"tempImg"}}):r("i",{staticClass:"el-icon-plus avatar-uploader-icon"})])],1)],1),r("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(e){t.visable=!1}}},[t._v("取 消")]),r("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.submit()}}},[t._v("确 定")])],1)],1),r("div",{staticClass:"video_header"},[r("span",[t._v("视频管理")]),r("button",{on:{click:function(e){return t.addVideo()}}},[t._v("添加视频")])]),r("div",{staticClass:"allVideo"},t._l(t.videoData,(function(e,n){return r("video-box",{key:n,attrs:{videoName:t.videoData[n].videoName,src:t.videoData[n].path,uploadTime:t.videoData[n].createTime}})})),1)],1)},o=[],i=(r("b0c0"),r("96cf"),r("1da1")),a=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"container"},[r("div",{staticClass:"video-container"},[r("video",{attrs:{src:t.src,id:t.videoName},on:{mouseover:t.ShowControls,mouseleave:t.HideControls}}),r("ul",[r("li",[t._v(t._s(t.videoName))]),r("li",[t._v(t._s(t.uploadTime))]),r("li",[r("a",{attrs:{href:"javascript:;"},on:{click:t.deleteVideo}},[t._v("删除")])])])])])},c=[],u={data:function(){return{}},inject:["reload"],props:["videoName","uploadTime","src"],methods:{deleteVideo:function(){var t=this;return Object(i["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,t.$http.delete("/file/video",{data:{videoName:t.videoName}});case 2:e.sent,t.reload();case 4:case"end":return e.stop()}}),e)})))()},ShowControls:function(){document.getElementById(this.videoName).controls=!0},HideControls:function(){document.getElementById(this.videoName).controls=!1}}},s=u,l=(r("3a03"),r("2877")),f=Object(l["a"])(s,a,c,!1,null,"15dc0679",null),d=f.exports,h={created:function(){this.getAllVideo()},data:function(){return{videoUrl:"",video:{},visable:!1,videoData:{},loading:!1,file:""}},provide:function(){return{reload:this.getAllVideo}},components:{videoBox:d},methods:{getAllVideo:function(){var t=this;return Object(i["a"])(regeneratorRuntime.mark((function e(){var r,n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return t.loading=!0,e.next=3,t.$http.get("/file/video2");case 3:if(r=e.sent,"token过期"!=r.data.msg&&"这个账号在别的地方登陆，请重新登录"!=r.data.msg){e.next=6;break}return e.abrupt("return",t.$message.error("登录状态过期，请重新登录"));case 6:for(t.videoData=r.data.data,n=0;n<t.videoData.length;n++)t.videoData[n].path="http://47.112.125.53:8080/"+t.videoData[n].path;t.loading=!1;case 9:case"end":return e.stop()}}),e)})))()},addVideo:function(){this.video={},this.videoUrl="",this.visable=!0},submit:function(){var t=this;return Object(i["a"])(regeneratorRuntime.mark((function e(){var r,n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(t.loading=!0,r=new window.FormData,t.file&&(r.append("file",t.file,t.file.name),r.append("videoName",t.video.name)),t.video.name){e.next=6;break}return t.$message.error("请完整填写"),e.abrupt("return");case 6:n={headers:{"Content-Type":"multipart/form-data"}},t.visable=!1,t.$http.put("/file/video",r,n).then((function(e){t.$message.success("添加成功"),t.loading=!1,t.getAllVideo()})).catch((function(e){t.$message.warning("添加失败"),t.loading=!1}));case 9:case"end":return e.stop()}}),e)})))()},beforeAvatarUpload:function(t){var e=this;this.file=t;var r=new FileReader;r.readAsDataURL(t),r.onloadend=function(){return r.result},setTimeout((function(){e.videoUrl=r.onloadend()}),500)}}},v=h,p=(r("89ed"),Object(l["a"])(v,n,o,!1,null,"0d372c92",null));e["default"]=p.exports},"53a7":function(t,e,r){},"68b9":function(t,e,r){},"89ed":function(t,e,r){"use strict";r("53a7")},"96cf":function(t,e,r){var n=function(t){"use strict";var e,r=Object.prototype,n=r.hasOwnProperty,o="function"===typeof Symbol?Symbol:{},i=o.iterator||"@@iterator",a=o.asyncIterator||"@@asyncIterator",c=o.toStringTag||"@@toStringTag";function u(t,e,r){return Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}),t[e]}try{u({},"")}catch(R){u=function(t,e,r){return t[e]=r}}function s(t,e,r,n){var o=e&&e.prototype instanceof m?e:m,i=Object.create(o.prototype),a=new C(n||[]);return i._invoke=k(t,r,a),i}function l(t,e,r){try{return{type:"normal",arg:t.call(e,r)}}catch(R){return{type:"throw",arg:R}}}t.wrap=s;var f="suspendedStart",d="suspendedYield",h="executing",v="completed",p={};function m(){}function g(){}function y(){}var w={};w[i]=function(){return this};var b=Object.getPrototypeOf,x=b&&b(b(D([])));x&&x!==r&&n.call(x,i)&&(w=x);var _=y.prototype=m.prototype=Object.create(w);function L(t){["next","throw","return"].forEach((function(e){u(t,e,(function(t){return this._invoke(e,t)}))}))}function E(t,e){function r(o,i,a,c){var u=l(t[o],t,i);if("throw"!==u.type){var s=u.arg,f=s.value;return f&&"object"===typeof f&&n.call(f,"__await")?e.resolve(f.__await).then((function(t){r("next",t,a,c)}),(function(t){r("throw",t,a,c)})):e.resolve(f).then((function(t){s.value=t,a(s)}),(function(t){return r("throw",t,a,c)}))}c(u.arg)}var o;function i(t,n){function i(){return new e((function(e,o){r(t,n,e,o)}))}return o=o?o.then(i,i):i()}this._invoke=i}function k(t,e,r){var n=f;return function(o,i){if(n===h)throw new Error("Generator is already running");if(n===v){if("throw"===o)throw i;return T()}r.method=o,r.arg=i;while(1){var a=r.delegate;if(a){var c=N(a,r);if(c){if(c===p)continue;return c}}if("next"===r.method)r.sent=r._sent=r.arg;else if("throw"===r.method){if(n===f)throw n=v,r.arg;r.dispatchException(r.arg)}else"return"===r.method&&r.abrupt("return",r.arg);n=h;var u=l(t,e,r);if("normal"===u.type){if(n=r.done?v:d,u.arg===p)continue;return{value:u.arg,done:r.done}}"throw"===u.type&&(n=v,r.method="throw",r.arg=u.arg)}}}function N(t,r){var n=t.iterator[r.method];if(n===e){if(r.delegate=null,"throw"===r.method){if(t.iterator["return"]&&(r.method="return",r.arg=e,N(t,r),"throw"===r.method))return p;r.method="throw",r.arg=new TypeError("The iterator does not provide a 'throw' method")}return p}var o=l(n,t.iterator,r.arg);if("throw"===o.type)return r.method="throw",r.arg=o.arg,r.delegate=null,p;var i=o.arg;return i?i.done?(r[t.resultName]=i.value,r.next=t.nextLoc,"return"!==r.method&&(r.method="next",r.arg=e),r.delegate=null,p):i:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,p)}function j(t){var e={tryLoc:t[0]};1 in t&&(e.catchLoc=t[1]),2 in t&&(e.finallyLoc=t[2],e.afterLoc=t[3]),this.tryEntries.push(e)}function O(t){var e=t.completion||{};e.type="normal",delete e.arg,t.completion=e}function C(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(j,this),this.reset(!0)}function D(t){if(t){var r=t[i];if(r)return r.call(t);if("function"===typeof t.next)return t;if(!isNaN(t.length)){var o=-1,a=function r(){while(++o<t.length)if(n.call(t,o))return r.value=t[o],r.done=!1,r;return r.value=e,r.done=!0,r};return a.next=a}}return{next:T}}function T(){return{value:e,done:!0}}return g.prototype=_.constructor=y,y.constructor=g,g.displayName=u(y,c,"GeneratorFunction"),t.isGeneratorFunction=function(t){var e="function"===typeof t&&t.constructor;return!!e&&(e===g||"GeneratorFunction"===(e.displayName||e.name))},t.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,y):(t.__proto__=y,u(t,c,"GeneratorFunction")),t.prototype=Object.create(_),t},t.awrap=function(t){return{__await:t}},L(E.prototype),E.prototype[a]=function(){return this},t.AsyncIterator=E,t.async=function(e,r,n,o,i){void 0===i&&(i=Promise);var a=new E(s(e,r,n,o),i);return t.isGeneratorFunction(r)?a:a.next().then((function(t){return t.done?t.value:a.next()}))},L(_),u(_,c,"Generator"),_[i]=function(){return this},_.toString=function(){return"[object Generator]"},t.keys=function(t){var e=[];for(var r in t)e.push(r);return e.reverse(),function r(){while(e.length){var n=e.pop();if(n in t)return r.value=n,r.done=!1,r}return r.done=!0,r}},t.values=D,C.prototype={constructor:C,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(O),!t)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=e)},stop:function(){this.done=!0;var t=this.tryEntries[0],e=t.completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var r=this;function o(n,o){return c.type="throw",c.arg=t,r.next=n,o&&(r.method="next",r.arg=e),!!o}for(var i=this.tryEntries.length-1;i>=0;--i){var a=this.tryEntries[i],c=a.completion;if("root"===a.tryLoc)return o("end");if(a.tryLoc<=this.prev){var u=n.call(a,"catchLoc"),s=n.call(a,"finallyLoc");if(u&&s){if(this.prev<a.catchLoc)return o(a.catchLoc,!0);if(this.prev<a.finallyLoc)return o(a.finallyLoc)}else if(u){if(this.prev<a.catchLoc)return o(a.catchLoc,!0)}else{if(!s)throw new Error("try statement without catch or finally");if(this.prev<a.finallyLoc)return o(a.finallyLoc)}}}},abrupt:function(t,e){for(var r=this.tryEntries.length-1;r>=0;--r){var o=this.tryEntries[r];if(o.tryLoc<=this.prev&&n.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var i=o;break}}i&&("break"===t||"continue"===t)&&i.tryLoc<=e&&e<=i.finallyLoc&&(i=null);var a=i?i.completion:{};return a.type=t,a.arg=e,i?(this.method="next",this.next=i.finallyLoc,p):this.complete(a)},complete:function(t,e){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&e&&(this.next=e),p},finish:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.finallyLoc===t)return this.complete(r.completion,r.afterLoc),O(r),p}},catch:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.tryLoc===t){var n=r.completion;if("throw"===n.type){var o=n.arg;O(r)}return o}}throw new Error("illegal catch attempt")},delegateYield:function(t,r,n){return this.delegate={iterator:D(t),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=e),p}},t}(t.exports);try{regeneratorRuntime=n}catch(o){Function("r","regeneratorRuntime = r")(n)}},b0c0:function(t,e,r){var n=r("83ab"),o=r("9bf2").f,i=Function.prototype,a=i.toString,c=/^\s*function ([^ (]*)/,u="name";n&&!(u in i)&&o(i,u,{configurable:!0,get:function(){try{return a.call(this).match(c)[1]}catch(t){return""}}})}}]);