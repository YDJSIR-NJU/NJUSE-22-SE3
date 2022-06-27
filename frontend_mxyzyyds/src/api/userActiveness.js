import axios from 'axios'
import {updateToken, USER_ACTIVENESS_MODULE} from "@/api/_prefix";

export const getActivenessDetail = (uid) => {
  return axios.get(`${USER_ACTIVENESS_MODULE}/detail?uid=${uid}`,
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

export const getActivenessTop = (uid, page) => {
  return axios.get(`${USER_ACTIVENESS_MODULE}/list?uid=${uid}&page=${page}`,
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

export const triggerUpdateWk = (uid) => {
  return axios.post(`${USER_ACTIVENESS_MODULE}/updateallWk?uid=${uid}`, {},
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

export const triggerUpdateMon = (uid) => {
  return axios.post(`${USER_ACTIVENESS_MODULE}/updateallMon?uid=${uid}`, {},
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
