import axios from 'axios'
import {updateToken, USER_MODULE} from './_prefix'

export const getUser = (uid) => {
  return axios.get(`${USER_MODULE}/get/${uid}`,
    {
      headers: {
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(
    res => {
      // console.log(res)
      updateToken(res)

      return res
    }
  )
};

export const loginUser = loginInfo => {
  const {
    id,
    uname,
    password,
    email,
    userRole,
    createTime
  } = loginInfo;
  return axios.post(`${USER_MODULE}/login`, {
    id,
    uname,
    password,
    email,
    userRole,
    createTime
  }).then(res => {
    updateToken(res)
    return res
  })
}

export const registerUser = registerInfo => {
  const {
    userVo,
    userDeviceVos
  } = registerInfo;
  return axios.post(`${USER_MODULE}/register`, {
    userVo,
    userDeviceVos
  })
}
export const getMyDevice = (uid, page) => {
  return axios.get(`${USER_MODULE}/devices/get/${uid}`,
    {
      params: {page: 1},
      headers: {Authorization: localStorage.getItem('token')}
    }).then(res => {
    updateToken(res)
    //console.log(res)
    return res
  })
}
export const deleteDevice = (uid, deviceId) => {
  return axios.post(`${USER_MODULE}/devices/delete?uid=${uid}&userDeviceRecordId=${deviceId}`, {}, {headers: {Authorization: localStorage.getItem('token')}}).then(res => {
    updateToken(res)
    console.log(res)
    return res;
  })
}
export const addDevice = (userid, operatingSystem, deviceBrand, id) => {
  return axios.post(`${USER_MODULE}/devices/add?uid=${userid}`, {
      userId: userid,
      operatingSystem: operatingSystem,
      deviceBrand: deviceBrand,
      id: id
    },
    {headers: {Authorization: localStorage.getItem('token')}}).then(res => {
    updateToken(res)
    console.log(res)
    return res
  })
}
export const updateDevice = (brand, system, id, uid) => {
  return axios.post(`${USER_MODULE}/devices/update?uid=${uid}`, {
      userId: uid,
      operatingSystem: system,
      deviceBrand: brand,
      id: id
    },
    {headers: {Authorization: localStorage.getItem('token')}}).then(res => {
    updateToken(res)
    console.log(res)
    return res
  })
}


