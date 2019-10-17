import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './item-participant.reducer';
import { IItemParticipant } from 'app/shared/model/item-participant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IItemParticipantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ItemParticipantDetail extends React.Component<IItemParticipantDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { itemParticipantEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ItemParticipant [<b>{itemParticipantEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="permission">Permission</span>
            </dt>
            <dd>{itemParticipantEntity.permission}</dd>
            <dt>
              <span id="whenCreated">When Created</span>
            </dt>
            <dd>
              <TextFormat value={itemParticipantEntity.whenCreated} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Project Participant</dt>
            <dd>{itemParticipantEntity.projectParticipant ? itemParticipantEntity.projectParticipant.id : ''}</dd>
            <dt>Recipient Item</dt>
            <dd>{itemParticipantEntity.recipientItem ? itemParticipantEntity.recipientItem.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/item-participant" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/item-participant/${itemParticipantEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ itemParticipant }: IRootState) => ({
  itemParticipantEntity: itemParticipant.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ItemParticipantDetail);
