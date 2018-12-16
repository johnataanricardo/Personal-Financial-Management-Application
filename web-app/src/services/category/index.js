import instance from '../instance';

export const getAllCategories = async () => {
  try {
    const response = await instance.get('/category/').then(response => { return response.data.data; });;
    return response;
  } catch (error) {
    console.log(error);
  }
}

export const remove = async (id) => {
  try {
    const response = await instance.delete('/category/' + id);
    return response;
  } catch (error) {
    console.log(error);
  }
}

export const post = async (category) => {
  try {
    const response = await instance.post('/category/', category).then(response => { return response.data.data; });
    return response;
  } catch (error) {
    console.log(error);
  }
}

export const put = async (category) => {
  try {
    const response = await instance.put('/category/', category);
    return response;
  } catch (error) {
    console.log(error);
  }
}