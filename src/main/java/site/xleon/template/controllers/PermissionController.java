package site.xleon.template.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import site.xleon.template.core.MyException;
import site.xleon.template.core.Result;
import site.xleon.template.mapper.PermissionMapper;
import site.xleon.template.models.PermissionEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author leon xu
 * @date 2021/6/2 11:15 上午
 */
@RestController
@RequestMapping("/permission")
@AllArgsConstructor
public class PermissionController {
  private PermissionMapper permissionMapper;

  @GetMapping()
  public Result<List<PermissionEntity>> all() {
    QueryWrapper<PermissionEntity> query = new QueryWrapper<>();
    query.select("*");
    List<PermissionEntity> data = permissionMapper.selectList(query);
    return Result.success(data);
  }

  /**
   * add permission
   *
   * @param params AddParams
   * @return Result
   * @throws MyException exception
   */
  @PostMapping()
  public Result<PermissionEntity> add(@RequestBody @Valid AddParams params) throws MyException {
    String title = params.title.trim();
    String detail = params.detail.trim();

    // title check
    QueryWrapper<PermissionEntity> query = new QueryWrapper<>();
    query.select("id").eq("title", title);
    PermissionEntity oldPermission = permissionMapper.selectOne(query);
    if (oldPermission != null) {
      throw new MyException("permission " + title + " already exist");
    }

    // parent check
    if (params.parentId > 0) {
      QueryWrapper<PermissionEntity> queryParent = new QueryWrapper<>();
      query.select("id").eq("id", params.parentId);
      PermissionEntity parent = permissionMapper.selectOne(queryParent);
      if (parent == null) {
        throw new MyException("parent id " + params.parentId + " invalid");
      }
    }

    PermissionEntity permission = new PermissionEntity();
    permission.setTitle(title);
    permission.setDetail(detail);
    permission.setParentId(params.parentId);
    permission.setIsMenu(params.isMenu);
    permission.setSeq(params.seq);
    permission.setApi(params.api.trim());
    permission.setApiMethod(params.apiMethod.trim());

    int count = permissionMapper.insert(permission);
    if (count != 1) {
      throw new MyException("add failure");
    }

    return Result.success(permission);
  }

  @Data
  private static class AddParams {
    @NotBlank
    private String title;
    private String detail = "";
    private Integer parentId = 0;
    private Boolean isMenu = false;
    private Integer seq = 0;
    private String api = "";
    private String apiMethod = "";
  }
}
