//>>built
(function(c){"object"===typeof module&&"object"===typeof module.exports?(c=c(require,exports),void 0!==c&&(module.exports=c)):"function"===typeof define&&define.amd&&define(["require","exports","../has/has","./global","./support/util"],c)})(function(c,a){function m(a){return a&&("symbol"===typeof a||"Symbol"===a["@@toStringTag"])||!1}Object.defineProperty(a,"__esModule",{value:!0});var q=c("../has/has"),n=c("./global"),b=c("./support/util");a.Symbol=n.default.Symbol;if(!q.default("es6-symbol")){var k=
function(a){if(!m(a))throw new TypeError(a+" is not a symbol");return a},l=Object.defineProperties,d=Object.defineProperty,r=Object.create,p=Object.prototype,e={},u=function(){var a=r(null);return function(c){for(var f=0,g;a[String(c)+(f||"")];)++f;c+=String(f||"");a[c]=!0;g="@@"+c;Object.getOwnPropertyDescriptor(p,g)||d(p,g,{set:function(a){d(this,g,b.getValueDescriptor(a))}});return g}}(),h=function t(a){if(this instanceof h)throw new TypeError("TypeError: Symbol is not a constructor");return t(a)};
a.Symbol=n.default.Symbol=function f(a){if(this instanceof f)throw new TypeError("TypeError: Symbol is not a constructor");var c=Object.create(h.prototype);a=void 0===a?"":String(a);return l(c,{__description__:b.getValueDescriptor(a),__name__:b.getValueDescriptor(u(a))})};d(a.Symbol,"for",b.getValueDescriptor(function(b){return e[b]?e[b]:e[b]=a.Symbol(String(b))}));l(a.Symbol,{keyFor:b.getValueDescriptor(function(a){var b;k(a);for(b in e)if(e[b]===a)return b}),hasInstance:b.getValueDescriptor(a.Symbol.for("hasInstance"),
!1,!1),isConcatSpreadable:b.getValueDescriptor(a.Symbol.for("isConcatSpreadable"),!1,!1),iterator:b.getValueDescriptor(a.Symbol.for("iterator"),!1,!1),match:b.getValueDescriptor(a.Symbol.for("match"),!1,!1),observable:b.getValueDescriptor(a.Symbol.for("observable"),!1,!1),replace:b.getValueDescriptor(a.Symbol.for("replace"),!1,!1),search:b.getValueDescriptor(a.Symbol.for("search"),!1,!1),species:b.getValueDescriptor(a.Symbol.for("species"),!1,!1),split:b.getValueDescriptor(a.Symbol.for("split"),!1,
!1),toPrimitive:b.getValueDescriptor(a.Symbol.for("toPrimitive"),!1,!1),toStringTag:b.getValueDescriptor(a.Symbol.for("toStringTag"),!1,!1),unscopables:b.getValueDescriptor(a.Symbol.for("unscopables"),!1,!1)});l(h.prototype,{constructor:b.getValueDescriptor(a.Symbol),toString:b.getValueDescriptor(function(){return this.__name__},!1,!1)});l(a.Symbol.prototype,{toString:b.getValueDescriptor(function(){return"Symbol ("+k(this).__description__+")"}),valueOf:b.getValueDescriptor(function(){return k(this)})});
d(a.Symbol.prototype,a.Symbol.toPrimitive,b.getValueDescriptor(function(){return k(this)}));d(a.Symbol.prototype,a.Symbol.toStringTag,b.getValueDescriptor("Symbol",!1,!1,!0));d(h.prototype,a.Symbol.toPrimitive,b.getValueDescriptor(a.Symbol.prototype[a.Symbol.toPrimitive],!1,!1,!0));d(h.prototype,a.Symbol.toStringTag,b.getValueDescriptor(a.Symbol.prototype[a.Symbol.toStringTag],!1,!1,!0))}a.isSymbol=m;"hasInstance isConcatSpreadable iterator species replace search split match toPrimitive toStringTag unscopables observable".split(" ").forEach(function(c){a.Symbol[c]||
Object.defineProperty(a.Symbol,c,b.getValueDescriptor(a.Symbol.for(c),!1,!1))});a.default=a.Symbol});