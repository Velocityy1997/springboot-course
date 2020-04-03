Math.radians = function(degrees) {
    return degrees * Math.PI / 180;
}
;
Math.degrees = function(radians) {
    return radians * 180 / Math.PI;
}
;
function getAngleFromPoint(point, centerPoint) {
    var dy = point.y - centerPoint.y
      , dx = point.x - centerPoint.x;
    var theta = Math.atan2(dy, dx);
    var angle = theta * 180 / Math.PI % 360;
    angle = angle < 0 ? 360 + angle : angle;
    return angle;
}
function getDistance(point1, point2) {
    var xs = 0;
    var ys = 0;
    xs = point2.x - point1.x;
    xs = xs * xs;
    ys = point2.y - point1.y;
    ys = ys * ys;
    return Math.sqrt(xs + ys);
}
Array.from(document.body.querySelectorAll('[class^="_"]')).map(ele=>{
    Array.from(ele.classList).map(sClass=>{
        var name = "_" + sClass.replace(/[-]/gi, "");
        if (typeof window[name] === "undefined") {
            window[name] = ele;
        } else {
            window[name] = [window[name]];
            window[name].push(ele);
        }
    }
    );
}
);
let points = [];
__svg.addEventListener("click", ({clientX, clientY})=>{
    const point = {
        x: clientX,
        y: clientY
    };
    storePoint(point);
}
);
function round(no) {
    return Math.round(no * 100) / 100;
}
__svg.addEventListener("mousemove", ({clientX, clientY})=>{
    const point = {
        x: clientX,
        y: clientY
    };
    window.lastKnownMousePosition = point;
    update(point);
}
);
function storePoint(point) {
    point = point || window.lastKnownMousePosition;
    if (points.length >= 1) {
        if (__old.innerHTML === '') {
            window.firstEverStoredMousePosition = points[0];
        }
        __old.innerHTML += `<g>${__current.innerHTML}</g>`;
        window.firstStoredMousePosition = points[0];
        window.lastStoredMousePosition = point;
        points = [];
    } else {
        points.push(point);
        window.firstStoredMousePosition = point;
    }
}
function update(point) {
    point = point || window.lastKnownMousePosition;
    if (points.length > 0) {
        const endPoint = point;
        const startPoint = points[points.length - 1];
        line(startPoint, endPoint);
    } 
}
function line(startPoint, endPoint) {
    const angle = round(getAngleFromPoint(endPoint, startPoint));
    const distance = round(getDistance(startPoint, endPoint));
    __path.setAttribute("d", `M ${startPoint.x} ${startPoint.y} L  ${endPoint.x} ${endPoint.y}`);
    // const textpos = distance > __info.getComputedTextLength() ? distance / 2 : 0;
    // const {x, y} = __path.getPointAtLength(textpos);
    // const rot = angle > 90 && angle < 270 ? round(angle - 180) : angle;
    // const transform = `rotate(${rot} ${x} ${y}) translate(0,-8)`;
    // __info.setAttribute("x", x);
    // __info.setAttribute("y", y);
    // __info.setAttribute("text-anchor", x === 0 ? "right" : "middle");
    // __info.setAttribute("transform", transform);
    // __info.textContent = info;
    return __path;
}
// function scaleImage() {
//     const {clientWidth, clientHeight} = document.body;
//     __image.setAttribute("width", clientWidth);
//     __image.setAttribute("height", clientHeight);
// }
// window.addEventListener("resize", scaleImage);
window.addEventListener("keydown", ({key})=>{
    if (window.firstEverStoredMousePosition) {
        if (points.length === 1) {
            points.pop();
        }
        let point;
        const keyType = key.toLowerCase();
        console.log(keyType);
        switch (keyType) {
        case 'control':
            point = lastStoredMousePosition;
            break;
        case 'shift':
            point = firstStoredMousePosition;
            break;
        case 'alt':
            point = firstEverStoredMousePosition;
            break;
        default:
            return;
            break;
        }
        points.push(point);
        update();
    }
}
);
// scaleImage();
