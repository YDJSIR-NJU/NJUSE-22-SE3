import axios from 'axios'
import {REPORT_MODULE, REPORT_REMARK_MODULE, SIM_REPORT_MODULE, updateToken} from './_prefix'

// 缺陷报告本体部分

export const submitReport = form => {
  return axios.post(`${REPORT_MODULE}/release`, form, {
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

export const reportList = (uid, page, queryID) => {
  return axios.get(`${REPORT_MODULE}/query/${queryID}?uid=${uid}&page=${page}`,
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

export const reportDetail = (uid, reportID) => {
  return axios.get(`${REPORT_MODULE}/detail?uid=${uid}&reportid=${reportID}`,
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

// 报告相似度部分

export const getSimReport = (uid, reportId, topN) => {
  return axios.get(`${SIM_REPORT_MODULE}/cal?uid=${uid}&reportId=${reportId}&topN=${topN}`,
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

export const getCorporateReport = (uid, reportId, topN) => {
  return axios.get(`${SIM_REPORT_MODULE}/recommand?uid=${uid}&reportId=${reportId}&topN=${topN}`,
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

export const getSimConfig = (uid) => {
  return axios.get(`${SIM_REPORT_MODULE}/getConfig?uid=${uid}`,
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

export const setSimConfig = (uid, rejectedScore, cooperationMin) => {
  return axios.post(`${SIM_REPORT_MODULE}/setConfig?uid=${uid}`,
    {
      rejectedScore,
      cooperationMin
    },
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

// 报告评价部分

export const releaseReportRemark = (uid, reportRemarkVo) => {
  return axios.post(`${REPORT_REMARK_MODULE}/release?uid=${uid}`, reportRemarkVo,
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

export const getReportRemarkByReport = (uid, page, reportId) => {
  return axios.get(`${REPORT_REMARK_MODULE}/query/${reportId}?uid=${uid}&page=${page}`,
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


//获取某一测试记录下的报告列表
export const getReportListByTestRecordId = (testRecordId, uid, page) => {
  return axios.get(`${REPORT_MODULE}/fetch/${testRecordId}?uid=${uid}&page=${page}`,
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

//获取某一测试记录下的低质量报告列表
export const getLowQualityReport = (taskid, uid, page) => {
  return axios.get(`${REPORT_MODULE}/lowQuality?taskId=${taskid}&uid=${uid}&page=${page}`,
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

export const reviewReport = (uid, reportReviewVo) => {
  return axios.post(`${REPORT_MODULE}/review?uid=${uid}`, {},
    {
      params: {
        reportReviewDto: reportReviewVo
      },
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

// 获取某份报告的评审状态
export const getReviewStatus = (uid, reportid) => {
  return axios.get(`${REPORT_MODULE}/getReview?uid=${uid}&reportId=${reportid}`,
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

export const getAvgScore = (uid, reportid) => {
  return axios.get(`${REPORT_REMARK_MODULE}/queryAvg?uid=${uid}&reportid=${reportid}`,
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

// 获取某份报告的平均分
export const getUserWords = (uid, k, keywordNum) => {
  return axios.get(`${REPORT_MODULE}/getKMeans?uid=${uid}&k=${k}&keyword=${keywordNum}`,
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
export const getReportWords = (uid, k, keyword, taskid,) => {
  return axios.get(`${REPORT_MODULE}/getWorkerKMeans?uid=${uid}&k=${k}&keyword=${keyword}&taskid=${taskid}`,
    {
      headers: {
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem("token")
      }
    }).then(res => {
    updateToken(res)
    return res
  })
}
export const getDistinctReport = (defectReportId) => {
  return axios.get(`${REPORT_MODULE}/getDistinctReport?defectReportId=${defectReportId}`, {
    headers: {
      // Authorization: store.state.token
      Authorization: window.localStorage.getItem("token")
    }
  }).then(res => {
    updateToken(res)
    return res
  })
}

export const lowQuality = (taskId, uid, page) => {
  return axios.get(`${REPORT_MODULE}/lowQuality?taskId=${taskId}&page=${page}&uid=${uid}`, {
    headers: {
      // Authorization: store.state.token
      Authorization: window.localStorage.getItem("token")
    }
  }).then(res => {
    updateToken(res)
    return res
  })
}

export const getReportScoreAvg = (uid, reportId) => {
  return axios.get(`${REPORT_REMARK_MODULE}/queryAvg?uid=${uid}&reportid=${reportId}`, {
    headers: {
      // Authorization: store.state.token
      Authorization: window.localStorage.getItem("token")
    }
  }).then(res => {
    updateToken(res)
    return res
  })
}

