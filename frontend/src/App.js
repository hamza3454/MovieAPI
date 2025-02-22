import './App.css';
import api from './api/axiosConfig';
import { useEffect } from 'react';
import { useState } from 'react';
import Layout from './components/Layout';
import {Routes, Route} from 'react-router-dom';
import Home from './components/home/Home';
import Header from './components/header/Header';
import Trailer from './components/trailer/Trailer';
import Reviews from './components/reviews/Reviews';
import NotFound from './components/notFound/NotFound';

function App() {

  const [movies, setMovies] = useState();
  const [movie, setMovie] = useState();
  const [reviews, setReviews] = useState([]);

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
  const getMovieData = async (movieId) => {
     
    try 
    {
        const response = await api.get(`/api/v1/movies/${movieId}`);

        const singleMovie = response.data;

        setMovie(singleMovie);
        console.log(singleMovie);

        setReviews(singleMovie.reviewIds);
        

    } 
    catch (error) 
    {
      console.error(error);
    }


  }

  // getMovies function exceuted when the app first loads
  useEffect(() => {
    getMovies();
  },[])

  return (
    <div className="App">
      <Header/>
      <Routes>
        <Route path="/" element={<Layout/>}>
            <Route path="/" element={<Home movies={movies}/>}></Route>
            <Route path="/Trailer/:ytTrailerId" element={<Trailer/>}></Route>
            <Route path="/Reviews/:movieId" element ={<Reviews getMovieData = {getMovieData} movie={movie} reviews ={reviews} setReviews = {setReviews} />}></Route>
            <Route path="*" element = {<NotFound/>}></Route>
        </Route>
      </Routes>
    </div>
  );
}

export default App;
