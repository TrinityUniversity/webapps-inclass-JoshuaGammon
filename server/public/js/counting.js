onload = () => {
    console.log("counting")
    document.getElementById("clicker").onclick = () => {
        console.log("clicked")
        fetch("/getNumber").then(r => r.text()).then(t => document.getElementById("cnt").innerHTML = t)
    }
}

const socketRoute = document.getElementById("ws-route").value;
const socket = new WebSocket(socketRoute.replace("http","ws"));