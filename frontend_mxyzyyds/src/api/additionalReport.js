import axios from 'axios'
import {ADDITIONAL_REPORT_MODULE, updateToken} from './_prefix'

export const getAdditionalDetail = (uid, additionalReportId) => {
  return axios.get(`${ADDITIONAL_REPORT_MODULE}/detail?uid=${uid}&additionalReportId=${additionalReportId}`,
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

export const submitAdditionalReport = (uid, form) => {
  return axios.post(`${ADDITIONAL_REPORT_MODULE}/release?uid=${uid}`, form, {
    headers: {
      'Content-Type': 'multipart/form-data',
      // Authorization: store.state.token
      Authorization: window.localStorage.getItem("token")
    }
  }).then(response => {
    updateToken(response)
    return response
  })
}

export const getAdditionalByReport = (uid, page, defectReportId) => {
  return axios.get(`${ADDITIONAL_REPORT_MODULE}/query/${defectReportId}?uid=${uid}&page=${page}`,
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

export const getAdditionalByReportAndUid = (uid, page, defectReportId) => {
  return axios.get(`${ADDITIONAL_REPORT_MODULE}/queryMy?uid=${uid}&page=${page}&defectReportId=${defectReportId}`,
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
