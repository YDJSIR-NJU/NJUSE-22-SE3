import axios from 'axios'
import {updateToken, USER_CAPABILITIES_MODULE} from "@/api/_prefix";

export const getUserCapabilitiesDetail = (uid) => {
  return axios.get(`${USER_CAPABILITIES_MODULE}/detail?uid=${uid}`,
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

export const getCapabilitiesTop = (uid, page) => {
  return axios.get(`${USER_CAPABILITIES_MODULE}/list?uid=${uid}&page=${page}`,
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

export const getReportDevicesSum = (uid) => {
  return axios.get(`${USER_CAPABILITIES_MODULE}/queryDev?uid=${uid}`,
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

export const getFinishedTaskSum = (uid) => {
  return axios.get(`${USER_CAPABILITIES_MODULE}/queryType?uid=${uid}`,
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
