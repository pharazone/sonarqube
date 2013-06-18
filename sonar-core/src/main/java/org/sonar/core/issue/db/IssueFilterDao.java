/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2013 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.sonar.core.issue.db;

import org.apache.ibatis.session.SqlSession;
import org.sonar.api.BatchComponent;
import org.sonar.api.ServerComponent;
import org.sonar.core.persistence.MyBatis;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

import java.util.List;

/**
 * @since 3.7
 */
public class IssueFilterDao implements BatchComponent, ServerComponent {

  private final MyBatis mybatis;

  public IssueFilterDao(MyBatis mybatis) {
    this.mybatis = mybatis;
  }

  @CheckForNull
  public IssueFilterDto selectById(Long id) {
    SqlSession session = mybatis.openSession();
    try {
      session.getMapper(IssueFilterMapper.class);
      return getMapper(session).selectById(id);
    } finally {
      MyBatis.closeQuietly(session);
    }
  }

  @CheckForNull
  public IssueFilterDto selectByNameAndUser(String name, String user, @Nullable Long existingId) {
    SqlSession session = mybatis.openSession();
    try {
      return getMapper(session).selectByNameAndUser(name, user, existingId);
    } finally {
      MyBatis.closeQuietly(session);
    }
  }

  public List<IssueFilterDto> selectByUser(String user) {
    SqlSession session = mybatis.openSession();
    try {
      return getMapper(session).selectByUser(user);
    } finally {
      MyBatis.closeQuietly(session);
    }
  }

  public List<IssueFilterDto> selectByUserWithOnlyFavoriteFilters(String user) {
    SqlSession session = mybatis.openSession();
    try {
      return getMapper(session).selectByUserWithOnlyFavoriteFilters(user);
    } finally {
      MyBatis.closeQuietly(session);
    }
  }

  public void insert(IssueFilterDto filter) {
    SqlSession session = mybatis.openSession();
    try {
      getMapper(session).insert(filter);
      session.commit();
    } finally {
      MyBatis.closeQuietly(session);
    }
  }

  public void update(IssueFilterDto filter) {
    SqlSession session = mybatis.openSession();
    try {
      getMapper(session).update(filter);
      session.commit();
    } finally {
      MyBatis.closeQuietly(session);
    }
  }

  public void delete(Long id) {
    SqlSession session = mybatis.openSession();
    try {
      getMapper(session).delete(id);
      session.commit();
    } finally {
      MyBatis.closeQuietly(session);
    }
  }

  private IssueFilterMapper getMapper(SqlSession session) {
    return session.getMapper(IssueFilterMapper.class);
  }
}
