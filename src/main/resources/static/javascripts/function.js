
document.addEventListener('DOMContentLoaded', function() {
    toDateFormat();
    toLocaleFormat();
    toRateFormat();
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

function toRateFormat(){
    var rates = document.getElementsByClassName("rate");
    for(var rate of rates){
        var origin = parseFloat(rate.innerHTML);
        var newVal = (origin*100).toFixed(2);
        var newHtml = '';
        var color = 'black';
        if(newVal > 0){
            newHtml = '+' + newVal + '%';
            color = 'red';
        }
        else if(newVal < 0){
            newHtml =  newVal + '%';
            color = 'blue';
        }
        else{
            newHtml = '+' + newVal + '%';
        }

        rate.innerHTML = '<span style = "color :' + color + '">' + newHtml + '</span>';
    }
  }