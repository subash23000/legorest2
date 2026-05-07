function showCount() {
    fetch("/rest/lego/count")
    .then(response => response.text())
    .then(data => {
        document.getElementById("stats").innerHTML =
        "Total records: " + data;
    })
    .catch(error => {
        document.getElementById("stats").innerHTML =
        "Error fetching count: ";
    });
}

function showAverage() {
    fetch("/rest/lego/averagespeed")
    .then(response => response.text())
    .then(data => {
        document.getElementById("stats").innerHTML =
        "Average speed: " + data;
    })
    .catch(error => {
        document.getElementById("stats").innerHTML =
        "Error fetching average speed: ";
    });
}