
document.addEventListener('DOMContentLoaded', function() {
    toDateFormat();
})

function toDateFormat(){
    dates = document.getElementsByClassName('date');

    for(var date of dates){
        date.innerText = date.innerText.substring(0, 10);
    }
}