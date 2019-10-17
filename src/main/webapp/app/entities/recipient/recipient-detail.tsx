import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './recipient.reducer';
import { IRecipient } from 'app/shared/model/recipient.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecipientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RecipientDetail extends React.Component<IRecipientDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { recipientEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Recipient [<b>{recipientEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="relationshipToYou">Relationship To You</span>
            </dt>
            <dd>{recipientEntity.relationshipToYou}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{recipientEntity.status}</dd>
            <dt>
              <span id="statusTLM">Status TLM</span>
            </dt>
            <dd>
              <TextFormat value={recipientEntity.statusTLM} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="whenCreated">When Created</span>
            </dt>
            <dd>
              <TextFormat value={recipientEntity.whenCreated} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Project</dt>
            <dd>{recipientEntity.project ? recipientEntity.project.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/recipient" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/recipient/${recipientEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ recipient }: IRootState) => ({
  recipientEntity: recipient.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RecipientDetail);
