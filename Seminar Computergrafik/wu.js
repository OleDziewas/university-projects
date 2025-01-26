//Quelle: Wikipedia

const plot = function(x, y, c, arr) {
    //Runden auf zweite Dezimalstelle
    //c = Math.round(x * 100) / 100;
    arr.push([x, y, c]);
}

const fpart = function(x){
    if (x < 0) {
        return x - (Math.floor(x) + 1);
    }
    return x - Math.floor(x);
};

const rfpart = function(x){
    return 1-fpart(x);
};

const drawLine = function(x0, y0, x1, y1){
    // Calculate if steep
    let res = [];
    const steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);

    // Swap inputs if needed for easier calculation
    if (steep) {
        [x0, y0] = [y0, x0];
        [x1, y1] = [y1, x1];
    }
    if (x0 > x1) {
       [x0, x1] = [x1, x0];
       [y0, y1] = [y1, y0];
    }

    //Calculate the gradient
    const dx = x1 - x0;
    const dy = y1 - y0;

    let gradient;
    if (dx === 0.0){
        gradient = 1.0;
    } else {
        gradient = dy / dx;
    }

    //Calculate the color values for every pixel by iterating over y or x depending on the steep value
    let intersectY = y0;
    if (steep) {
        for (let x = x0 ; x <=x1 ; x++){
            // pixel coverage is determined by fractional
            // part of y co-ordinate
            plot(Math.floor(intersectY), x, rfpart(intersectY), res);
            plot(Math.floor(intersectY)+1, x, fpart(intersectY), res);
            intersectY += gradient;
        }
    } else {
        for (let x = x0 ; x <= x1 ; x++) {
            // pixel coverage is determined by fractional
            // part of y co-ordinate
            plot(x, Math.floor(intersectY), rfpart(intersectY), res);
            plot(x, Math.floor(intersectY)+1, fpart(intersectY), res);
            intersectY += gradient;
        }
    }
    return res;
};

const createGitter = function(){
    let rects = [];
    const svg = document.getElementById("svg");
    for (let x = 1; x <= 8; x++) {
        for (let y = 1; y <= 5; y++) {
            let rect = document.createElementNS('http://www.w3.org/2000/svg', "rect");
            rect.setAttribute('id', `rect${x}${y}`);
            rect.setAttribute("height", 100);
            rect.setAttribute("width", 100);
            rect.setAttribute("style", "fill:white; stroke:black; stroke-width:0.5;");
            rect.setAttribute("x", `${x*100-100}`);
            rect.setAttribute("y", `${y*100-100}`);
            rects.push(rect);
            svg.appendChild(rect);
        }
    }
    return rects;
}
document.getElementById("dbut").addEventListener("click", drawWu);
createGitter();

function drawWu(){
    svg.innerHTML ='';
    let rects = createGitter();

    const SF = 100;
    const x0 = parseInt(document.getElementById("x0").value);
    const y0 = parseInt(document.getElementById("y0").value);
    const x1 = parseInt(document.getElementById("x1").value);
    const y1 =parseInt(document.getElementById("y1").value);

    const baseline = document.createElementNS('http://www.w3.org/2000/svg', "line");
    baseline.setAttribute('id', "baseline");
    baseline.setAttribute('style', "stroke:rgb(255,0,0); stroke-width:2");
    baseline.setAttribute("x1", x0*SF-(SF*0.5));
    baseline.setAttribute("y1", y0*SF-(SF*0.5));
    baseline.setAttribute("x2", x1*SF-(SF*0.5));
    baseline.setAttribute("y2", y1*SF-(SF*0.5));
    svg.appendChild(baseline);
    res = drawLine(x0, y0, x1, y1);
    console.log(res);
    console.log(rects);
    for (let i = 0; i < res.length; i++){
       
        let color = grayscaleToHex(255-parseInt(res[i][2]*255, 10));
        console.log("color: ", color);
        let index = res[i][0]*5-5 + res[i][1]-1;
        console.log("index: ", index);
        rects[index].setAttribute('style', `fill:${color};stroke:black; stroke-width:0.5;`);
        
    }
            
}

function grayscaleToHex(grayValue) {
    const hex = grayValue.toString(16).padStart(2, '0'); // Konvertiert Graustufenwert in 2-stelligen Hex-Wert
    return "#" + hex.repeat(3); // Setzt R-, G-, und B-Komponente des Hex-Werts gleich
}