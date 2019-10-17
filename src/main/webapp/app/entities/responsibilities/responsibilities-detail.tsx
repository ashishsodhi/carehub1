import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './responsibilities.reducer';
import { IResponsibilities } from 'app/shared/model/responsibilities.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResponsibilitiesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ResponsibilitiesDetail extends React.Component<IResponsibilitiesDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { responsibilitiesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Responsibilities [<b>{responsibilitiesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{responsibilitiesEntity.description}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{responsibilitiesEntity.status}</dd>
            <dt>
              <span id="whenCreated">When Created</span>
            </dt>
            <dd>
              <TextFormat value={responsibilitiesEntity.whenCreated} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Recipient</dt>
            <dd>{responsibilitiesEntity.recipient ? responsibilitiesEntity.recipient.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/responsibilities" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/responsibilities/${responsibilitiesEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ responsibilities }: IRootState) => ({
  responsibilitiesEntity: responsibilities.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ResponsibilitiesDetail);
