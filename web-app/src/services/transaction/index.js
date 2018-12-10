import instance from '../instance';

export const save = async (transaction) => {
  if (transaction.id > 0) {
    return put(transaction);
  } else {
    return post(transaction);
  }
}

export const post = async (transaction) => {
  try {
    const response = await instance.post('/transaction/', transaction);
    return response;
  } catch (error) {
    console.log(error);
  }
}

export const put = async (transaction) => {
  try {
    const response = await instance.put('/transaction/', transaction);
    return response;
  } catch (error) {
    console.log(error);
  }
}

export const remove = async (id) => {
  try {
    const response = await instance.delete('/transaction/' + id);
    return response;
  } catch (error) {
    console.log(error)
  }
}