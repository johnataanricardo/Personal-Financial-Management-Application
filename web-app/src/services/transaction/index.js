import instance from '../instance';

export const post = async (transaction) => {
  try {
    const response = await instance.post('/transaction/', transaction);
    return response;
  } catch (error) {
    console.log(error);
  }
}