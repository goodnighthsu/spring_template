package site.xleon.template.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.xleon.template.core.Result;
import site.xleon.template.core.Utils;
import site.xleon.template.mapper.LogMapper;
import site.xleon.template.models.LogEntity;


/**
 * @author leon xu
 * @date 2021/6/17 1:42 下午
 */
@RestController
@RequestMapping("/log")
@AllArgsConstructor
public class LogController {
  private LogMapper logMapper;

  @GetMapping()
  public Result<Page<LogEntity>> list(
    @RequestParam(value = "page", required = false) Integer page,
    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
    Page<LogEntity> paging = Utils.page(page, pageSize);
    QueryWrapper<LogEntity> query = new QueryWrapper<>();
    query.select("*").orderByDesc("id");
    Page<LogEntity> logs = logMapper.selectPage(paging, query);

    return Result.success(logs);
  }
}
