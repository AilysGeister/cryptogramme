isVisible = true;

function menuButton() {
    if (isVisible) {
        hideByID("summary");
        isVisible = false;
    } else {
        showByID("summary");
        isVisible = true;
    }
}

function showByID(id){
    document.getElementById(id).style.display = "block";
}

function hideByID(id) {
    document.getElementById(id).style.display = "none";
}