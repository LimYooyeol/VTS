
document.addEventListener('DOMContentLoaded', function() {
    toDateFormat();
    toLocaleFormat();
})

function toDateFormat(){
    dates = document.getElementsByClassName('date');

    for(var date of dates){
        date.innerText = date.innerText.substring(0, 10);
    }
}

function toLocaleFormat(){
    numbers = document.getElementsByClassName('number');

    for(var num of numbers){
        num.innerText = parseInt(num.innerText).toLocaleString();
    }
}