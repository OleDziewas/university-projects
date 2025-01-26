window.onload = function(){

// Select a dataset for the assignment
var datasetName = "olympics.json"; // possible values are satellites.json or olympics.json

d3.json(datasetName).then(function(data){

        // Draw a sample force-directed graph. Modify the code or write another function for your solution.
        drawSampleGraph(data);

    });  
}

function drawSampleGraph(data)
{
    // The force-directed graph code is from https://d3js.org/d3-force

    // Specify the dimensions of the chart.
    const width = 800;
    const height = 600;


    // Specify the color scale.
    const color = d3.scaleOrdinal(d3.schemeCategory10);
  
    // The force simulation mutates links and nodes, so create a copy
    // so that re-evaluating this cell produces the same result.
    const links = data.links.map(d => ({...d}));
    const nodes = data.nodes.map(d => ({...d}));
  
    let countryIDs = nodes.filter(d => d.type === "country").map(d => d.id)
    let categoryIDs = nodes.filter(d => d.type === "category").map(d => d.id)

    let athletesPerCategory = {};
    for (let id of categoryIDs){
        let athletesPerLink = links.filter(d => d.source === id).map(data => new Set(data.children.map(d => d.athlete.name)))
        let athletesInCountry = new Set();
        athletesPerLink.forEach(set => set.forEach(value => athletesInCountry.add(value)));
        athletesPerCategory[id] =athletesInCountry;
    }

    console.log(athletesPerCategory)

    let medalsPerCountry = {};
    let athletesPerCountry = {};
    for (let id of countryIDs){
        medalsPerCountry[id] = links.filter(d => d.target === id).map(d => d.weight).reduce((acc, val) => acc + val, 0);
        let athletesPerLink = links.filter(d => d.target === id).map(data => new Set(data.children.map(d => d.athlete.name)))
        let athletesInCountry = new Set();
        athletesPerLink.forEach(set => set.forEach(value => athletesInCountry.add(value)));
        athletesPerCountry[id] =athletesInCountry;
    }

    let numberOfMedals = Object.values(medalsPerCountry);
    const nodeScale= d3.scaleLinear()
        .domain([Math.min(...numberOfMedals), Math.max(...numberOfMedals)])
        .range([8, 20]);

    let uniqueAthleteNumbers = links.map(data => (new Set(data.children.map(d => d.athlete.name))).size)
    const linkScale = d3.scaleLinear()
        .domain([Math.min(...uniqueAthleteNumbers), Math.max(...uniqueAthleteNumbers)])
        .range([0.5, 15]);

    // Create a simulation with several forces.
    const simulation = d3.forceSimulation(nodes)
        .force("link", d3.forceLink(links).id(d => d.id).distance(200))
        .force("charge", d3.forceManyBody())
        .force("x", d3.forceX())
        .force("y", d3.forceY())
        .force("collision", d3.forceCollide().radius(20));

    // Select the SVG container.
    const svg = d3.select("svg")
        .attr("width", width)
        .attr("height", height)
        .attr("viewBox", [-width / 2, -height / 2, width, height])
        .attr("style", "max-width: 100%; height: auto;");

    // Add a line for each link, and a circle for each node.
    const link = svg.append("g")
        .attr("stroke", "#999")
        .attr("stroke-opacity", 0.6)
    .selectAll("line")
    .data(links)
    .join("line")
        .attr("stroke-width", d => linkScale(getLinkWidth(d)))

    let selectedNode = null;

    const node = svg.append("g")
        .attr("stroke", "#fff")
        .attr("stroke-width", 1.5)
    .selectAll("circle")
    .data(nodes)
    .join("circle")
        .attr("r", d => nodeScale(getNodeSize(d, medalsPerCountry, athletesPerCategory)))
        .attr("fill", d => color(d.type))
        .on("click", function(event, d){
            event.stopPropagation();
            if (selectedNode === d) {
                selectedNode = null;
                link.attr("stroke-opacity", 0.6);
                svg.selectAll("circle")
                    .attr("stroke", "#fff")
                    .attr("stroke-width", 2);
            } else {
                link.attr("stroke-opacity", 0);
                // Remove border from all circles first
                svg.selectAll("circle")
                    .attr("stroke", "#fff")
                    .attr("stroke-width", 2);

                // Select the clicked node
                selectedNode = d;
                // Show links connected to the clicked node
                link.filter(l => l.source === d || l.target === d)
                    .attr("stroke-opacity", 0.6);

                // Apply border to the clicked circle
                d3.select(this)
                    .attr("stroke", "black")
                    .attr("stroke-width", 3);

                const detailsDiv = d3.select("#selection_details");
                detailsDiv.html("");
                let countryHeading = detailsDiv.append("h1")
                countryHeading.html(d.id)
                const listOfAthletes = detailsDiv.append("ul");
                let length = d.type === "country" ? athletesPerCountry[d.id].size : athletesPerCategory[d.id].size;
                let array = d.type === "country" ? Array.from(athletesPerCountry[d.id]) : Array.from(athletesPerCategory[d.id]);
                for (let i=0; i<length; i++){
                    let athlete = listOfAthletes.append("li");
                    if (d.type ==="country"){
                        athlete.html(array[i]);
                    } else {
                        athlete.html(array[i]);
                    }
                }
            }
            
        });
    
    svg.on("click", function() {
        selectedNode = null;
        link.attr("stroke-opacity", 0.6);
        
        // Rahmen aller Nodes zurücksetzen
        svg.selectAll("circle")
            .attr("stroke", "#fff")
            .attr("stroke-width", 2);
        
        // Details-Div zurücksetzen oder leeren
        const detailsDiv = d3.select("#selection_details");
        detailsDiv.html(""); // Hier kannst du auch den Inhalt leeren oder eine Nachricht anzeigen
    });

    node.append("title")
        .text(d => d.id);
    
    // Add a drag behavior.
    node.call(d3.drag()
        .on("start", dragstarted)
        .on("drag", dragged)
        .on("end", dragended));

    svg.append("circle")
        .attr("cx", 280) 
        .attr("cy", -200)
        .attr("r", 10)
        .attr("fill", "black")
    
    svg.append("text")
        .attr("x", 300)
        .attr("y", -195) 
        .attr("font-size", "10px") 
        .attr("fill", "black")
        .text("Medals of a Country"); 
    
    svg.append("text")
        .attr("x", 300)
        .attr("y", -180) 
        .attr("font-size", "10px") 
        .attr("fill", "black")
        .text("or Athletes in a Oly."); 
    
    svg.append("text")
        .attr("x", 300)
        .attr("y", -165) 
        .attr("font-size", "10px") 
        .attr("fill", "black")
        .text("Category"); 
    
    
    
    svg.append("rect")
        .attr("x", 272) 
        .attr("y", -235)
        .attr("width", 17)
        .attr("height", 17)
        .attr("fill", "#ff7f0e")

    svg.append("text")
        .attr("x", 300)
        .attr("y", -223)
        .attr("font-size", "10px") 
        .attr("fill", "black")
        .text("Countries"); 

    svg.append("rect")
        .attr("x", 272) 
        .attr("y", -263)
        .attr("width", 17)
        .attr("height", 17)
        .attr("fill", "#1f77b4")
    
    svg.append("text")
        .attr("x", 300)
        .attr("y", -250) 
        .attr("font-size", "10px") 
        .attr("fill", "black")
        .text("Olymp. Categories"); 
            
    svg.append("text")

  
    // Set the position attributes of links and nodes each time the simulation ticks.
    simulation.on("tick", () => {
        link
            .attr("x1", d => d.source.x)
            .attr("y1", d => d.source.y)
            .attr("x2", d => d.target.x)
            .attr("y2", d => d.target.y);

        node
            .attr("cx", d => d.x)
            .attr("cy", d => d.y);
    });

    // Repeat the simulation when drag starts, and fix the subject position.
    function dragstarted(event) {
        if (!event.active) simulation.alphaTarget(0.3).restart();
        event.subject.fx = event.subject.x;
        event.subject.fy = event.subject.y;
    }

    // Update the subject (dragged node) position during drag.
    function dragged(event) {
        event.subject.fx = event.x;
        event.subject.fy = event.y;
    }

    // Restore the target alpha so the simulation cools after dragging ends.
    // Unfix the subject position now that it’s no longer being dragged.
    function dragended(event) {
        if (!event.active) simulation.alphaTarget(0);
        event.subject.fx = null;
        event.subject.fy = null;
    }

    function getLinkWidth(data) {
        return (new Set(data.children.map(d => d.athlete.name))).size;
    }

    function getNodeSize(data, medalsPerCountry, athletesPerCategory){
        return data.type === "country" ? medalsPerCountry[data.id] : athletesPerCategory[data.id].size;
    }
}