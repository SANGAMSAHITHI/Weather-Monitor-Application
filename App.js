import React, { useEffect, useState } from 'react';
import axios from 'axios';
import WeatherSummaryChart from './WeatherSummaryChart';

const App = () => {
    const [summary, setSummary] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await axios.get("http://localhost:8080/api/weather/summaries");
                setSummary(result.data);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };
        fetchData();
    }, []);

    const handleUpdateWeather = async () => {
        try {
            await axios.post("http://localhost:8080/api/weather/update");
            // Refresh data after update
            fetchData();
        } catch (error) {
            console.error("Error updating weather:", error);
        }
    };

    return (
        <div className="App">
            <h1>Weather Monitoring Dashboard</h1>
            <button onClick={handleUpdateWeather}>Update Weather</button>
            <WeatherSummaryChart data={summary} />
        </div>
    );
};

export default App;
