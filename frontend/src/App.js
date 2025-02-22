import './App.css';
import api from './api/axiosConfig';
import { useEffect } from 'react';
import { useState } from 'react';
import Layout from './components/Layout';
import {Routes, Route} from 'react-router-dom';
import Home from './components/home/Home';

function App() {

  const [movies, setMovies] = useState([]); 

  // handles https get request to an endpoint that returns an array of movie data 
  const getMovies = async () => {

    try {

      const response = await api.get("/api/v1/movies");

      // log the results returned from the call 
      console.log(response.data);

      setMovies(response.data);

    }
    catch(err){

      console.log(err);

    }
  }

  // getMovies function exceuted when the app first loads
  useEffect(() => {
    getMovies();
  },[])

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Layout/>}>
            <Route path="/" element={<Home movies={movies}/>}></Route>
            
        </Route>
      </Routes>
    </div>
  );
}

export default App;
