-- =========================================================
-- Speakly Admin System Database Schema
-- Database: PostgreSQL
-- Purpose:
-- Compatible with art-design-pro admin template
-- =========================================================


-- =========================================================
-- 1. 后台用户表
-- =========================================================
CREATE TABLE admin_user (
    id BIGSERIAL PRIMARY KEY, -- 用户主键ID

    username VARCHAR(50) NOT NULL UNIQUE, -- 登录用户名（唯一）
    password_hash VARCHAR(255) NOT NULL, -- BCrypt加密后的密码

    gender SMALLINT DEFAULT 1, -- 性别：1=男，0=女
    mobile VARCHAR(30), -- 手机号码
    email VARCHAR(100), -- 邮箱地址

    department VARCHAR(100), -- 所属部门

    status VARCHAR(10) DEFAULT '1', -- 用户状态：1=正常 2=禁用 3=冻结 4=异常

    avatar VARCHAR(500), -- 用户头像URL地址

    create_by VARCHAR(50), -- 创建人
    update_by VARCHAR(50), -- 更新人

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 更新时间
);

COMMENT ON TABLE admin_user IS '后台管理员用户表';

COMMENT ON COLUMN admin_user.id IS '用户主键ID';
COMMENT ON COLUMN admin_user.username IS '登录用户名';
COMMENT ON COLUMN admin_user.password_hash IS 'BCrypt加密后的密码';
COMMENT ON COLUMN admin_user.gender IS '性别：1=男，0=女';
COMMENT ON COLUMN admin_user.mobile IS '手机号';
COMMENT ON COLUMN admin_user.email IS '邮箱';
COMMENT ON COLUMN admin_user.department IS '所属部门';
COMMENT ON COLUMN admin_user.status IS '用户状态';
COMMENT ON COLUMN admin_user.avatar IS '头像URL';
COMMENT ON COLUMN admin_user.create_by IS '创建人';
COMMENT ON COLUMN admin_user.update_by IS '更新人';
COMMENT ON COLUMN admin_user.created_at IS '创建时间';
COMMENT ON COLUMN admin_user.updated_at IS '更新时间';



-- =========================================================
-- 2. 后台角色表
-- =========================================================
CREATE TABLE admin_role (
    id BIGSERIAL PRIMARY KEY, -- 角色主键ID

    role_name VARCHAR(50) NOT NULL, -- 角色名称
    role_code VARCHAR(50) NOT NULL UNIQUE, -- 角色编码（唯一）

    description VARCHAR(255), -- 角色描述

    enabled BOOLEAN DEFAULT TRUE, -- 是否启用：true=启用 false=禁用

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 更新时间
);

COMMENT ON TABLE admin_role IS '后台角色表';

COMMENT ON COLUMN admin_role.id IS '角色主键ID';
COMMENT ON COLUMN admin_role.role_name IS '角色名称';
COMMENT ON COLUMN admin_role.role_code IS '角色编码';
COMMENT ON COLUMN admin_role.description IS '角色描述';
COMMENT ON COLUMN admin_role.enabled IS '是否启用';
COMMENT ON COLUMN admin_role.created_at IS '创建时间';
COMMENT ON COLUMN admin_role.updated_at IS '更新时间';



-- =========================================================
-- 3. 用户角色关联表
-- =========================================================
CREATE TABLE admin_user_role (
     id BIGSERIAL PRIMARY KEY, -- 用户角色关联主键ID

     user_id BIGINT NOT NULL, -- 用户ID
     role_id BIGINT NOT NULL, -- 角色ID

     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间

     CONSTRAINT fk_admin_user_role_user
         FOREIGN KEY (user_id)
             REFERENCES admin_user(id)
             ON DELETE CASCADE,

     CONSTRAINT fk_admin_user_role_role
         FOREIGN KEY (role_id)
             REFERENCES admin_role(id)
             ON DELETE CASCADE,

     CONSTRAINT uk_admin_user_role
         UNIQUE (user_id, role_id)
);

COMMENT ON TABLE admin_user_role IS '用户角色关联表';

COMMENT ON COLUMN admin_user_role.id IS '关联主键ID';
COMMENT ON COLUMN admin_user_role.user_id IS '用户ID';
COMMENT ON COLUMN admin_user_role.role_id IS '角色ID';
COMMENT ON COLUMN admin_user_role.created_at IS '创建时间';



-- =========================================================
-- 4. 后台菜单表
-- =========================================================
CREATE TABLE admin_menu (
    id BIGSERIAL PRIMARY KEY, -- 菜单主键ID

    parent_id BIGINT, -- 父级菜单ID（顶级菜单为NULL）

    name VARCHAR(100) NOT NULL, -- 路由名称
    path VARCHAR(200) NOT NULL, -- 路由路径
    component VARCHAR(200), -- 前端组件路径

    title VARCHAR(200), -- 菜单标题
    icon VARCHAR(100), -- 菜单图标

    keep_alive BOOLEAN DEFAULT FALSE, -- 是否缓存页面
    is_hide BOOLEAN DEFAULT FALSE, -- 是否隐藏菜单
    is_hide_tab BOOLEAN DEFAULT FALSE, -- 是否隐藏Tab标签
    is_full_page BOOLEAN DEFAULT FALSE, -- 是否全屏页面
    is_first_level BOOLEAN DEFAULT FALSE, -- 是否一级菜单

    active_path VARCHAR(200), -- 当前激活菜单路径
    link VARCHAR(500), -- 外部链接地址

    sort_order INTEGER DEFAULT 0, -- 菜单排序

    enabled BOOLEAN DEFAULT TRUE, -- 是否启用

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 更新时间

    CONSTRAINT fk_admin_menu_parent
        FOREIGN KEY (parent_id)
            REFERENCES admin_menu(id)
            ON DELETE CASCADE
);

COMMENT ON TABLE admin_menu IS '后台菜单表';

COMMENT ON COLUMN admin_menu.id IS '菜单主键ID';
COMMENT ON COLUMN admin_menu.parent_id IS '父级菜单ID';
COMMENT ON COLUMN admin_menu.name IS '路由名称';
COMMENT ON COLUMN admin_menu.path IS '路由路径';
COMMENT ON COLUMN admin_menu.component IS '前端组件路径';
COMMENT ON COLUMN admin_menu.title IS '菜单标题';
COMMENT ON COLUMN admin_menu.icon IS '菜单图标';
COMMENT ON COLUMN admin_menu.keep_alive IS '是否缓存页面';
COMMENT ON COLUMN admin_menu.is_hide IS '是否隐藏菜单';
COMMENT ON COLUMN admin_menu.is_hide_tab IS '是否隐藏Tab';
COMMENT ON COLUMN admin_menu.is_full_page IS '是否全屏页面';
COMMENT ON COLUMN admin_menu.is_first_level IS '是否一级菜单';
COMMENT ON COLUMN admin_menu.active_path IS '激活菜单路径';
COMMENT ON COLUMN admin_menu.link IS '外部链接';
COMMENT ON COLUMN admin_menu.sort_order IS '菜单排序';
COMMENT ON COLUMN admin_menu.enabled IS '是否启用';
COMMENT ON COLUMN admin_menu.created_at IS '创建时间';
COMMENT ON COLUMN admin_menu.updated_at IS '更新时间';



-- =========================================================
-- 5. 角色菜单关联表
-- =========================================================
CREATE TABLE admin_role_menu (
         id BIGSERIAL PRIMARY KEY, -- 角色菜单关联主键ID

         role_id BIGINT NOT NULL, -- 角色ID
         menu_id BIGINT NOT NULL, -- 菜单ID

         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间

         CONSTRAINT fk_admin_role_menu_role
             FOREIGN KEY (role_id)
                 REFERENCES admin_role(id)
                 ON DELETE CASCADE,

         CONSTRAINT fk_admin_role_menu_menu
             FOREIGN KEY (menu_id)
                 REFERENCES admin_menu(id)
                 ON DELETE CASCADE,

         CONSTRAINT uk_admin_role_menu
             UNIQUE (role_id, menu_id)
);

COMMENT ON TABLE admin_role_menu IS '角色菜单关联表';

COMMENT ON COLUMN admin_role_menu.id IS '关联主键ID';
COMMENT ON COLUMN admin_role_menu.role_id IS '角色ID';
COMMENT ON COLUMN admin_role_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN admin_role_menu.created_at IS '创建时间';



-- =========================================================
-- 6. 按钮权限表
-- =========================================================
CREATE TABLE admin_button (
      id BIGSERIAL PRIMARY KEY, -- 按钮权限主键ID

      button_code VARCHAR(100) NOT NULL UNIQUE, -- 按钮权限编码
      button_name VARCHAR(100) NOT NULL, -- 按钮名称

      description VARCHAR(255), -- 按钮权限描述

      enabled BOOLEAN DEFAULT TRUE, -- 是否启用

      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 创建时间
);

COMMENT ON TABLE admin_button IS '按钮权限表';

COMMENT ON COLUMN admin_button.id IS '按钮权限主键ID';
COMMENT ON COLUMN admin_button.button_code IS '按钮权限编码';
COMMENT ON COLUMN admin_button.button_name IS '按钮名称';
COMMENT ON COLUMN admin_button.description IS '按钮描述';
COMMENT ON COLUMN admin_button.enabled IS '是否启用';
COMMENT ON COLUMN admin_button.created_at IS '创建时间';



-- =========================================================
-- 7. 角色按钮权限关联表
-- =========================================================
CREATE TABLE admin_role_button (
   id BIGSERIAL PRIMARY KEY, -- 角色按钮关联主键ID

   role_id BIGINT NOT NULL, -- 角色ID
   button_id BIGINT NOT NULL, -- 按钮ID

   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间

   CONSTRAINT fk_admin_role_button_role
       FOREIGN KEY (role_id)
           REFERENCES admin_role(id)
           ON DELETE CASCADE,

   CONSTRAINT fk_admin_role_button_button
       FOREIGN KEY (button_id)
           REFERENCES admin_button(id)
           ON DELETE CASCADE,

   CONSTRAINT uk_admin_role_button
       UNIQUE (role_id, button_id)
);

COMMENT ON TABLE admin_role_button IS '角色按钮权限关联表';

COMMENT ON COLUMN admin_role_button.id IS '关联主键ID';
COMMENT ON COLUMN admin_role_button.role_id IS '角色ID';
COMMENT ON COLUMN admin_role_button.button_id IS '按钮权限ID';
COMMENT ON COLUMN admin_role_button.created_at IS '创建时间';



-- =========================================================
-- 初始化角色数据
-- =========================================================
INSERT INTO admin_role (
    role_name,
    role_code,
    description,
    enabled
)
VALUES
    ('超级管理员', 'R_SUPER', '拥有系统全部权限', TRUE),
    ('管理员', 'R_ADMIN', '拥有后台管理权限', TRUE),
    ('内容编辑', 'R_EDITOR', '负责课程与内容管理', TRUE),
    ('客服专员', 'R_SUPPORT', '负责用户支持与反馈', TRUE),
    ('普通用户', 'R_USER', '普通后台用户', TRUE);



-- =========================================================
-- 初始化超级管理员账号
-- 默认密码建议后续使用 BCrypt
-- 当前仅用于初始化开发环境
-- =========================================================
INSERT INTO admin_user (
    username,
    password_hash,
    gender,
    mobile,
    email,
    department,
    status,
    avatar,
    create_by
)
VALUES (
           'Super',
           '$2a$10$7EqJtq98hPqEX7fNZaFWoOHi7x6v2bQJ8F8nM5QJ5r5M9X1k8vYwG',
           1,
           '18888888888',
           'admin@speakly.com',
           '系统管理部',
           '1',
           'https://i.pravatar.cc/300',
           'system'
       );



-- =========================================================
-- 给超级管理员绑定 R_SUPER 角色
-- =========================================================
INSERT INTO admin_user_role (
    user_id,
    role_id
)
VALUES (
           1,
           1
       );