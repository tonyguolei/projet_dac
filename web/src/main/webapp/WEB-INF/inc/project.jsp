<%@page import="java.util.List"%>
<%@page import="mybeans.Bonus"%>
<%@page import="java.util.Map"%>
<%@page import="mybeans.User"%>
<%
    if (request.getAttribute("project") == null || request.getAttribute("fundLevel") == null || request.getAttribute("comments") == null || request.getAttribute("bonus") == null || request.getAttribute("userFundLevel") == null || request.getAttribute("memorised") == null) {
        request.getRequestDispatcher("ControllerProject?action=inspect").forward(request, response);
        return;
    }
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="jumbotron">
  <h1>${requestScope.project.title}<small class="by"> by <a href="index.jsp?nav=user&id=${requestScope.project.idOwner.idUser}">${requestScope.project.idOwner.mail}</a></small></h1>
  <div>
    <c:if test="${sessionScope.user != null}">
      <c:choose>
        <c:when test="${!requestScope.memorised}">
          <a class="tooltip-need badge back-primary" href="ControllerMemorise?action=create&id=${requestScope.project.idProject}" title="Remember"><span class="glyphicon glyphicon-eye-open"></span></a>
        </c:when>
        <c:otherwise>
          <a class="tooltip-need badge" href="ControllerMemorise?action=remove&id=${requestScope.project.idProject}" title="Forget"><span class="glyphicon glyphicon-eye-close"></span></a>
        </c:otherwise>
      </c:choose>
      <a class="pull-right tooltip-need badge back-danger are-you-sure" href="ControllerProject?action=report&id=${requestScope.project.idProject}" title="Report this project"><span class="glyphicon glyphicon-exclamation-sign"></span></a>
    </c:if>
  </div>
  <hr>
  <div class="row project-stats">
    <ul>
      <li>
        <div class="project-stats-value"><span class="number">${100 * requestScope.fundLevel / requestScope.project.goal}</span>%</div>
        <span class="project-stats-label">funded</span>
      </li>
      <li>
        <div class="project-stats-value">$ <span class="number">${requestScope.fundLevel}</span></div>
        <span class="project-stats-label">pledged</span>
      </li>
      <li>
        <span class="project-stats-label">out of</span>
        <div class="project-stats-value">$ <span class="number">${requestScope.project.goal}</span></div>
      </li>
      <li>
        <span class="project-stats-label">Created</span>
        <div class="project-stats-value time-relative" data-format="YYYY-MM-DD HH:mm"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${requestScope.project.creationDate}" /></div>
      </li>
      <li>
        <span class="project-stats-label">Ends</span>
        <div class="project-stats-value time-relative" data-format="YYYY-MM-DD HH:mm"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${requestScope.project.endDate}" /></div>
      </li>
    </ul>
  </div>
  <div class="progress">
    <div class="progress-bar progress-bar-success fund-level" role="progressbar" aria-valuenow="0" data-value="${requestScope.fundLevel}" aria-valuemin="0" aria-valuemax="${requestScope.project.goal}" style="width: 0%;">
    </div>
  </div>
  <div class="row">
    <div class="col-sm-4 col-xs-12">
      <div class="project-tags-box">
        <div class="project-tags">${requestScope.project.tags}</div>
      </div>
    </div>
    <div class="col-sm-8 col-xs-12">
      <div class="row">
          <form class="form-inline" role="form" id="fundForm" name="fundForm" method="POST" action="ControllerFund?action=create&id=${requestScope.project.idProject}">
          <div class="form-group col-sm-5 col-xs-12">
            <label class="sr-only" for="value">Amount (in dollars)</label>
            <div class="input-group input-group-lg">
              <div class="input-group-addon">$</div>
              <input type="number" min="0" step="any" class="form-control" name="value" id="value" placeholder="Amount"/>
            </div>
          </div>
          <div class="col-sm-7 col-xs-12">
            <button class="btn btn-success btn-lg btn-block" id="btnFund" name="btnFund" type="button">Fund!</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <hr>
  <ul class="nav nav-tabs nav-justified" role="tablist">
    <li role="presentation" class="active"><a href="#story" aria-controls="story" role="tab" data-toggle="tab">Story</a></li>
    <li role="presentation"><a href="#bonus" aria-controls="bonus" role="tab" data-toggle="tab">Bonuses</a></li>
    <li role="presentation"><a href="#comments" aria-controls="comments" role="tab" data-toggle="tab">Comments</a></li>
      <c:if test="${sessionScope.user.idUser == requestScope.project.idOwner.idUser}">
      <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">Settings</a></li>
      <li role="presentation"><a href="#bonus_orders" aria-controls="bonus_orders" role="tab" data-toggle="tab">Bonuses orders</a></li>
      </c:if>
      <c:if test="${sessionScope.user.isAdmin}">
      <li role="presentation"><a href="#admin" aria-controls="admin" role="tab" data-toggle="tab">Admin</a></li>
      </c:if>
  </ul>
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane active markdown enable-video" id="story" >${requestScope.project.description}</div>
    <div role="tabpanel" class="tab-pane" id="bonus">
      <c:choose>
          <c:when test="${bonus.size() > 0}">
              <c:forEach items="${bonus}" var="bonus">
                  <p>
                    <c:if test="${sessionScope.user != null}">
                        <c:set value="glyphicon-remove" var="iconName"></c:set>
                        <c:if test="${userFundLevel.compareTo(bonus.value)>=0}">
                            <c:set value="glyphicon-ok text-success" var="iconName"></c:set>
                        </c:if>
                        <span class="glyphicon ${iconName}" aria-hidden="true"></span>
                    </c:if>
                    Title:&nbsp${bonus.title}
                  </p>
                  <p>Value:&nbsp${bonus.value}</p>
                  <div class="markdown">Description:&nbsp${bonus.description}</div>
              </c:forEach>
          </c:when>
          <c:otherwise>
              <p>There are no bonuses for this project!</p>
          </c:otherwise>
      </c:choose>
      <c:if test="${sessionScope.user.idUser == requestScope.project.idOwner.idUser}">
          <hr>
          <p>You can create new bonuses here. People are more likely to fund a project with bonuses. Keep that in mind!</p>
          <div role="tabpanel" class="tab-pane" id="create_bonus">
            <br/>
            <form role="form" method="POST" action="ControllerBonus">
              <input type="hidden" name="action" value="create"/>
              <input type="hidden" name="id" value="${requestScope.project.idProject}">
              <div class="form-group">
                <div class="row">
                  <div class="form-group col-sm-6">
                    <input type="text" class="form-control" name="title" placeholder="Title" required>
                  </div>
                  <div class="form-group col-sm-6">
                    <input type="number" min="0" step="any" class="form-control" name="value" placeholder="Value" required>
                  </div>
                </div>
                <div class="editor-wrapper">
                  <textarea id="description_bonus" class="editor" data-height="160" data-maxlen="500" placeholder="Description" name="description"></textarea>
                </div>
              </div>
              <button class="btn btn-lg btn-warning btn-block" type="submit">Create bonus</button>
            </form>
          </div>
      </c:if>
    </div>
    <div role="tabpanel" class="tab-pane" id="comments">
      <!--<h2>Comments</h2> -->
      <c:forEach items="${comments}" var="comment">
          <div class="media">
            <a class="media-left" href="#">
              <img data-src="img/profile-placeholder.jpg" src="img/profile-placeholder.jpg" class="profile-img" alt="${comment.idUser.mail}">
            </a>
            <div class="media-body">
              <h6 class="media-heading">${comment.idUser.mail} <span class="time-relative" data-format="YYYY-MM-DD HH:mm"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${comment.date}" /></span></h6>
              <div class="markdown smaller">${comment.comment}</div>
            </div>
          </div>
      </c:forEach>
      <form role="form" method="POST" action="ControllerComment">
        <input type="hidden" name="action" value="create"/>
        <input type="hidden" name="id" value="${requestScope.project.idProject}"/>
        <div class="form-group">
          <div class="editor-wrapper">
            <textarea class="editor" data-height="160" data-maxlen="500" placeholder="Comment here ...." name="content"></textarea>
          </div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Comment</button>
      </form>
    </div>
    <c:if test="${sessionScope.user.idUser == requestScope.project.idOwner.idUser}">
        <div role="tabpanel" class="tab-pane" id="settings">
          <br/>
          <form role="form" method="POST" action="ControllerProject">
            <input type="hidden" name="action" value="getEditPage"/>
            <input type="hidden" name="id" value="${requestScope.project.idProject}">
            <button class="btn btn-lg btn-warning btn-block" type="submit">Edit project</button>
          </form>
          <br/>
          <form role="form" method="POST" action="ControllerProject">
            <input type="hidden" name="action" value="delete"/>
            <input type="hidden" name="id" value="${requestScope.project.idProject}">
            <button class="btn btn-lg btn-danger btn-block are-you-sure" type="submit">Delete project</button>
          </form>
        </div>
        <c:if test="${!userByBonus.isEmpty()}">
            <div role="tabpanel" class="tab-pane" id="bonus_orders">
              <br/>
              <ul>
                <c:forEach items="${userByBonus.entrySet()}" var="userBonusEntry">
                    <c:set value="${userBonusEntry.getValue().size()}" var="listSize"></c:set>
                    <li> <p> ${userBonusEntry.getKey().title} <small> (${listSize} backer${listSize>1?"s":""}) </small> </p> </li>
                      <c:if test="${listSize > 0}">
                      <ul>
                        <c:forEach items="${userBonusEntry.getValue()}" var="backer">
                            <li> 
                              <a href="index.jsp?nav=user&id=${backer.idUser}">${backer.mail}</a>
                            </li>
                        </c:forEach>
                      </ul>
                    </c:if>
                </c:forEach>
              </ul>
            </div>
        </c:if>
    </c:if>
    <c:if test="${sessionScope.user.isAdmin}">
        <div role="tabpanel" class="tab-pane" id="admin">
          <br/>
          <form role="form" method="POST" action="ControllerProject">
            <input type="hidden" name="action" value="getEditPage"/>
            <input type="hidden" name="id" value="${requestScope.project.idProject}">
            <button class="btn btn-lg btn-warning btn-block" type="submit">Edit project</button>
          </form>
          <br/>
          <form role="form" method="POST" action="ControllerProject">
            <input type="hidden" name="action" value="delete"/>
            <input type="hidden" name="id" value="${requestScope.project.idProject}">
            <button class="btn btn-lg btn-danger btn-block are-you-sure" type="submit">Delete project</button>
          </form>
        </div>
    </c:if>
  </div>
</div>
