import instance from '../instance';

export const validateToken = async (token) => {
  try {
    instance.defaults.headers['Authorization'] = 'Bearer ' + JSON.parse(token);
    const response = await instance.post('/auth/valid/', token).then(response => { return response.data.data });
    return response;
  } catch (error) {
    console.log(error);
  }
}