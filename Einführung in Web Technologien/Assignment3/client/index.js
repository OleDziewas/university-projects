async function loadD3DiagramAndTreeCount() {
    // Load data
    const fetched_results = await fetch(
        "http://localhost:3000/goto-mars/stats", {mode: "cors"}
    );
    const results = await fetched_results.json();
    const data = results.data;
    
    // Calculate tree count
    let co2_sum = 0;
    for (let i = 0; i < data.length; i++){
        co2_sum += data[i].co2;
    }
    const tree_count = co2_sum * 80;
    const p_tree = document.querySelector("#tree_count");
    p_tree.innerHTML = tree_count.toLocaleString('de-DE');
    console.log(tree_count);
    // Find min and max values for cost, co2 and seats
    const min_cost = data.reduce((obj1, obj2) => obj1.cost < obj2.cost ? obj1 : obj2)["cost"];
    const max_cost = data.reduce((obj1, obj2) => obj1.cost > obj2.cost ? obj1 : obj2)["cost"];
    const cost_diff = max_cost - min_cost;
    const min_co2 = data.reduce((obj1, obj2) => obj1.co2 < obj2.co2 ? obj1 : obj2)["co2"];
    const max_co2 = data.reduce((obj1, obj2) => obj1.co2 > obj2.co2 ? obj1 : obj2)["co2"];
    const co2_diff = max_co2 - min_co2;
    const min_seats = data.reduce((obj1, obj2) => obj1.seats < obj2.seats ? obj1 : obj2)["seats"];
    const max_seats = data.reduce((obj1, obj2) => obj1.seats > obj2.seats ? obj1 : obj2)["seats"];

    // set the dimensions and margins of the graph
    const margin = { top: 10, right: 20, bottom: 50, left: 60 },
    width = 500 - margin.left - margin.right,
    height = 420 - margin.top - margin.bottom;

    // append the svg object to the body of the page
    const svg = d3
        .select("#my_dataviz")
        .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", `translate(${margin.left},${margin.top})`);


    // Add X axis
    const scalar = 5;
    const x = d3.scaleLinear().domain([min_cost-(cost_diff / scalar), max_cost + (cost_diff / scalar)]).range([0, width]);
    svg
    .append("g")
    .attr("transform", `translate(0, ${height})`)
    .call(d3.axisBottom(x));

    // Add Y axis
    const y = d3.scaleLinear().domain([min_co2-(co2_diff / scalar), max_co2+(co2_diff / scalar)]).range([height, 0]);
    svg.append("g").call(d3.axisLeft(y));

    // Add a scale for bubble size
    const z = d3.scaleLinear().domain([min_seats, max_seats]).range([4, 20]);

    // Add dots
    svg
        .append("g")
        .selectAll("dot")
        .data(data)
        .join("circle")
        .attr("cx", (d) => x(d.cost))
        .attr("cy", (d) => y(d.co2))
        .attr("r", (d) => z(d.seats))
        .style("fill", "#7195ff")
        .style("opacity", "0.7")
        .attr("stroke", "black")
        .style("stroke-width", "1px");

    // Add x-axis label
    svg.append("text")
        .attr("x", width / 2)
        .attr("y", height + margin.top + 30)
        .style("text-anchor", "middle")
        .text("Kosten pro Rakete in Euro");

    // Add y-axis label
    svg.append("text")
        .attr("transform", "rotate(-90)")
        .attr("x", -(height / 2))
        .attr("y", -margin.left)
        .attr("dy", "1em")
        .style("text-anchor", "middle")
        .text("CO2 Emissionen in Tonnen");
}

loadD3DiagramAndTreeCount();



