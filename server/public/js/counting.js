

onload = () => {
    console.log("counting")
    document.getElementById("clicker").onclick = () => {
        console.log("clicked")
        fetch("/getNumber").then(r => r.text()).then(t => document.getElementById("cnt").innerHTML = t)
    }
}