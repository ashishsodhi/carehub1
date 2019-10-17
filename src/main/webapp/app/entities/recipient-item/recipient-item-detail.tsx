import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './recipient-item.reducer';
import { IRecipientItem } from 'app/shared/model/recipient-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecipientItemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RecipientItemDetail extends React.Component<IRecipientItemDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { recipientItemEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            RecipientItem [<b>{recipientItemEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="permissionToAll">Permission To All</span>
            </dt>
            <dd>{recipientItemEntity.permissionToAll ? 'true' : 'false'}</dd>
            <dt>
              <span id="whenCreated">When Created</span>
            </dt>
            <dd>
              <TextFormat value={recipientItemEntity.whenCreated} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Task</dt>
            <dd>{recipientItemEntity.task ? recipientItemEntity.task.id : ''}</dd>
            <dt>Document</dt>
            <dd>{recipientItemEntity.document ? recipientItemEntity.document.id : ''}</dd>
            <dt>Recipient</dt>
            <dd>{recipientItemEntity.recipient ? recipientItemEntity.recipient.id : ''}</dd>
            <dt>Message Item</dt>
            <dd>{recipientItemEntity.messageItem ? recipientItemEntity.messageItem.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/recipient-item" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/recipient-item/${recipientItemEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ recipientItem }: IRootState) => ({
  recipientItemEntity: recipientItem.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RecipientItemDetail);
