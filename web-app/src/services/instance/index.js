import axios from 'axios';
const apiUrl = process.env.API_URL;
const token = 'Bearer ' + localStorage.getItem('token');

const instance = axios.create({
  baseURL: apiUrl,
  headers: {       
    'Content-Type': 'application/json',
    'Authorization': token
  }
});

export default instance;