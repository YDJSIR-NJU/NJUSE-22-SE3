package com.assignment.collect.controller.activeness;

import com.assignment.collect.dto.ActivityDTO.ActivityDTO;
import com.assignment.collect.service.activeness.ActivenessService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.JWTUtils;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.activeness.ActivenessVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/activeness")
@Api(tags = "ActivenessController")
public class ActivenessController {

    @Autowired
    private ActivenessService activenessService;

    /**
     * TODO管理员身份验证
     */

    @ApiOperation("updateallWk")
    @PostMapping(value = "/updateallWk")
    public ResultVo<List<ActivenessVo>> updateAllWk(@RequestParam Integer uid,
                                                    HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        return activenessService.updateAllWk();

    }

    @ApiOperation("updateallMon")
    @PostMapping(value = "/updateallMon")
    public ResultVo<List<ActivenessVo>> updateAllMon(@RequestParam Integer uid,
                                                     HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        return activenessService.updateAllMon();

    }

    @ApiOperation("detail")
    @GetMapping("/detail")
    public ResultVo<ActivenessVo> getDetail(@RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);
        assert uid != null;
        return activenessService.getDetail(uid);
    }

    @ApiOperation("list")
    @GetMapping("/list")
    public PageInfo<ActivityDTO> getList(@RequestParam Integer uid,
                                         @RequestParam Integer page,
                                         HttpServletRequest httpServletRequest) {
        /**
         * TODO 发包方角色验证
         */
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);
        assert uid != null;
        return activenessService.getList(uid, page, Constant.PAGE_SIZE);
    }
}
