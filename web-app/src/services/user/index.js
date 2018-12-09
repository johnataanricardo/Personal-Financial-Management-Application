import instance from '../instance';

export const getUserLogged = async () => {
  try {
    const response = await instance.get('/user/').then(response => { return response.data.data; });
    return response;
  } catch (error) {
    console.log(error);
  }
}

export const post = async (user) => {
  try {
    const response = await instance.post('/user/sign-up/', user).then(response => { 
      return response.data.data.token == null ? '' : response.data.data.token;
    });
    return response;
  } catch (error) {
    console.log(error);
  }
};

export const put = async (user) => {
  try {
    const response = await instance.put('/user/', user);
    return response;
  } catch (error) {
    console.log(error);
  }
}