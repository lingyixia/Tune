var myAudio;
var playBut;
$(document).ready(function () {
    myAudio = document.getElementById("myAudio");
    playBut = document.getElementById("playBut");
    myAudio.onloadstart = initTime();
    myAudio.onloadstart = initPlayPosition();

    playBut.onclick = function () {
        playOrPause();
    };
});

/*
初始化时间,无论是否在播放
 */
function initTime() {
    var currentTime = document.getElementById("currentTime");
    currentTime.innerHTML = myAudio.duration;
}

/*
初始化播放位置，无论当前是否在播放
 */
function initPlayPosition() {
    var audioProgressBackground = document.getElementById("audioProgressBackground");
    var audioProgress = document.getElementById("audioProgress");
    audioProgressBackground.onclick = function () {
        var mousePosition = mousePosition(e)[0] - getElementLeft(audioProgressBackground)
        var percent= mousePosition/ parseInt(getStyle(this).width);
    }
}

var progressTimer;

/*
播放按钮
 */
function playOrPause() {
    if (myAudio.paused) {
        myAudio.play();
        progressTimer = window.setInterval(updateAudioProgress, 100);
    }
    else {
        myAudio.pause();
    }
}

/*
更新当前进度条
 */
function updateAudioProgress() {
    var progressVal = (myAudio.currentTime / myAudio.duration) * 465;
    console.log(document.getElementById("audioProgressBackground").style.width)
    document.getElementById("audioProgress").style.width = progressVal + "px";
}

/*
更新当前时间
 */
function updateCurrentTime(currentTime) {

}

/*
设置时间和进度条
 */
function setAudioProgressAndTime() {
    if (myAudio.currentTime < myAudio.duration) {
        if (!myAudio.paused) {
            setAudioProgress();
        }
        else {
            clearInterval(progressTimer);
        }
    }
}

//得到元素的绝对位置  left
function getElementLeft(element) {
    var actualLeft = element.offsetLeft;
    var current = element.offsetParent;
    while (current !== null) {
        actualLeft += current.offsetLeft;
        current = current.offsetParent;
    }
    return actualLeft;
}

/*
获取鼠标位置
 */
function mousePosition(evt) {
    var xPos, yPos;
    evt = evt || window.event;
    if (evt.pageX) {
        xPos = evt.pageX;
        yPos = evt.pageY;
    } else {
        xPos = evt.clientX + document.body.scrollLeft - document.body.clientLeft;
        yPos = evt.clientY + document.body.scrollTop - document.body.clientTop;
    }
    return [xPos, yPos];
}