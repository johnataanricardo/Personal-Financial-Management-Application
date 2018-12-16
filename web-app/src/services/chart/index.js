import instance from '../instance';

export const getChartByYear = async (year) => {
  try {
    const response = await instance.get('/chart/' + year).then(response => { return response.data.data; });
    return response;
  } catch (error) {
    console.log(error);
  }
}