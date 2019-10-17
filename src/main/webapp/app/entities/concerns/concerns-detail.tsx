import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './concerns.reducer';
import { IConcerns } from 'app/shared/model/concerns.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConcernsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ConcernsDetail extends React.Component<IConcernsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { concernsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Concerns [<b>{concernsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{concernsEntity.description}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{concernsEntity.status}</dd>
            <dt>
              <span id="whenCreated">When Created</span>
            </dt>
            <dd>
              <TextFormat value={concernsEntity.whenCreated} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Recipient</dt>
            <dd>{concernsEntity.recipient ? concernsEntity.recipient.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/concerns" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/concerns/${concernsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ concerns }: IRootState) => ({
  concernsEntity: concerns.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ConcernsDetail);
