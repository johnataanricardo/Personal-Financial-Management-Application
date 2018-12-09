import instance from '../instance';

export const getTransactionsByMonthAndYear = async (year, month) => {
  try {
    const response = await instance.get('/home/' + year + '/' + month).then(response => { return response.data.data; });
    return response;
  } catch (error) {
    console.log(error);
  }
}