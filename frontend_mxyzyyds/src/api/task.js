import axios from 'axios'
import {TASK_MODULE, TASK_RATING_MODULE, updateToken} from './_prefix'


export const allTask = (uid, page) => {
  return axios.get(`${TASK_MODULE}/all/${uid}?page=${page}`,
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
};

export const addTask = form => {
  return axios.post(`${TASK_MODULE}/release`, form, {
      headers: {
        'Content-Type': 'multipart/form-data',
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(response => {
    updateToken(response)
    return response
  })
};

export const myTask = (uid, page) => {
  return axios.get(`${TASK_MODULE}/published/${uid}?page=${page}`,
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
};

export const myHistry = (uid, page) => {
  return axios.get(`${TASK_MODULE}/history/${uid}?page=${page}`,
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

export const myProcessing = (uid, page) => {
  return axios.get(`${TASK_MODULE}/processing/${uid}?page=${page}`,
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

export const taskDetail = (queryID, uid) => {
  return axios.get(`${TASK_MODULE}/detail?taskid=${queryID}&uid=${uid}`, {
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

export const selectTask = (uid, queryID) => {
  return axios.post(`${TASK_MODULE}/select?uid=${uid}&taskid=${queryID}`, {},
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

export const taskPlayground = (uid, page) => {
  return axios.get(`${TASK_MODULE}/list/${page}?uid=${uid}`, {
    headers: {
      // Authorization: store.state.token
      Authorization: window.localStorage.getItem("token")
    }
  }).then(response => {
    updateToken(response)
    return response
  })
}

export const getTestRecord = (uid, testRecordID) => {
  return axios.get(`${TASK_MODULE}/testRecord?uid=${uid}&testrecordid=${testRecordID}`, {
    headers: {
      // Authorization: store.state.token
      Authorization: window.localStorage.getItem("token")
    }
  }).then(response => {
    updateToken(response)
    return response
  })
}

export const checkTaskStatus = (uid, taskid) => {
  return axios.post(`${TASK_MODULE}/check?uid=${uid}&taskid=${taskid}`, {}, {
    headers: {
      // Authorization: store.state.token
      Authorization: window.localStorage.getItem("token")
    }
  })
    .then(response => {
      updateToken(response)
      return response
    })
}

export const downloadDocs = (uid, taskid) => {
  return axios.get(`${TASK_MODULE}/downloadDocs?userId=${uid}&taskid=${taskid}`, {
    headers: {
      // Authorization: store.state.token
      Authorization: window.localStorage.getItem("token")
    }
  })
    .then(response => {
      console.log("DOWNLOAD")
      updateToken(response)
      return response
    })
}
export const getRecommendUsers = (taskid) => {
  return axios.get(`${TASK_MODULE}/getRecommendations`, {
    params: {
      taskId: taskid
    },
    headers: {
      Authorization: window.localStorage.getItem("token")
    }
  }).then(res => {
    updateToken(res)
    return res
  })
}

// 获取某份报告的平均分
export const getAvgScore = (uid, taskid) => {
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
export const getCloseInfo = (taskid) => {
  return axios.get(`${TASK_MODULE}/checkIsClosed?taskId=${taskid}`, {headers: {Authorization: window.localStorage.getItem("token")}}).then(res => {
    updateToken(res)
    return res
  })
}
export const closeTask = (taskid) => {
  return axios.get(`${TASK_MODULE}/close?taskId=${taskid}`, {
    headers: {
      Authorization: window.localStorage.getItem("token")
    }
  }).then(res => {
    updateToken(res)
    return res
  })
}
