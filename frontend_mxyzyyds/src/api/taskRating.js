import axios from 'axios'
import {TASK_RATING_MODULE, updateToken} from "./_prefix";

export const getTaskRatings = (uid, page, taskid) => {
  return axios.get(`${TASK_RATING_MODULE}/getTaskRatings?page=${page}&uid=${uid}&taskid=${taskid}`,
    {
      headers: {
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(response => {
    updateToken(response)
    return response
  })
}

export const getRatingStatus = (uid, taskid) => {
  return axios.get(`${TASK_RATING_MODULE}/getRatings?uid=${uid}&taskid=${taskid}`,
    {
      headers: {
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(response => {
    updateToken(response)
    return response
  })
}

export const submitRating = form => {
  return axios.post(`${TASK_RATING_MODULE}/rank`, form,
    {
      headers: {
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(response => {
    updateToken(response)
    return response
  })
}

export const getTaskRatingPer = (uid, taskid) => {
  return axios.get(`${TASK_RATING_MODULE}/getTaskRatingPer?uid=${uid}&taskid=${taskid}`,
    {
      headers: {
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(response => {
    updateToken(response)
    return response
  })
}
