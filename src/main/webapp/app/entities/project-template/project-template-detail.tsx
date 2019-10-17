import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './project-template.reducer';
import { IProjectTemplate } from 'app/shared/model/project-template.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjectTemplateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProjectTemplateDetail extends React.Component<IProjectTemplateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { projectTemplateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ProjectTemplate [<b>{projectTemplateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="serviceId">Service Id</span>
            </dt>
            <dd>{projectTemplateEntity.serviceId}</dd>
            <dt>
              <span id="templateDescription">Template Description</span>
            </dt>
            <dd>{projectTemplateEntity.templateDescription}</dd>
            <dt>
              <span id="templateCreationClass">Template Creation Class</span>
            </dt>
            <dd>{projectTemplateEntity.templateCreationClass}</dd>
            <dt>
              <span id="whenCreated">When Created</span>
            </dt>
            <dd>{projectTemplateEntity.whenCreated}</dd>
          </dl>
          <Button tag={Link} to="/entity/project-template" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/project-template/${projectTemplateEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ projectTemplate }: IRootState) => ({
  projectTemplateEntity: projectTemplate.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectTemplateDetail);
