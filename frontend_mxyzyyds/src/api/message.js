import axios from 'axios'
import {MESSAGE_MODULE, updateToken} from './_prefix'

export const getAllMessages = (uid, page) => {
  return axios.get(`${MESSAGE_MODULE}/get/${uid}?page=${page}`,
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
export const setRead = (messageid, uid) => {
  return axios.post(`${MESSAGE_MODULE}/read?messageid=${messageid}&uid=${uid}`, {},
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
export const sendInviteMsg = (uid, context) => {
  return axios.post(`${MESSAGE_MODULE}/write`,
    {

      id: 0,
      userId: uid,
      time: '',
      status: '',
      content: context

    }, {
      headers: {
        Authorization: window.localStorage.getItem("token")
      }
    }).then(res => {
    updateToken(res)
    return res
  })
}
export const setAllRead = (uid) => {
  return axios.post(`${MESSAGE_MODULE}/allRead?uid=${uid}`, {},
    {
      headers: {
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem('token')
      }
    }
  ).then(res =>{
    updateToken(res)
    return res
  })
}
export const getUnRead = (uid, page) => {
  return axios.get(`${MESSAGE_MODULE}/get/new/${uid}?page=${page}`, {
    headers: {
      // Authorization: store.state.token
      Authorization: window.localStorage.getItem('token')
    }
  }).then(res => {
    updateToken(res)
    return res
  })
}
