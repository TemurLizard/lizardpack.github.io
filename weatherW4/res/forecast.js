fetch('http://localhost:8080/weather')
    .then(response => response.json())
    .then(response => {
        console.log(response);


        let temp = response.main.temp.toFixed(0)

        document.getElementById("city").innerHTML = response.name;
        document.getElementById("temp").innerHTML = temp - 273 + "°C";
        document.getElementById("pressure").innerHTML = "Pressure " + response.main.pressure ;

        // let weatherDataDiv = document.getElementById('weatherData');
        // data.forEach(entry => {
        //     let city = entry.city;
        //     let temperature = entry.temperature;
        //     let pressure = entry.pressure;
        //     weatherDataDiv.innerHTML += `<p>City: ${city}, Temperature: ${temperature}, Pressure: ${pressure}</p>`;
        // });
    })
    .catch(err => {
        console.log(err);
    });
    // .catch(error => console.error('Error:', error));