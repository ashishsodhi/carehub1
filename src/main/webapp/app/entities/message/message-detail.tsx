import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './message.reducer';
import { IMessage } from 'app/shared/model/message.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMessageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MessageDetail extends React.Component<IMessageDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { messageEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Message [<b>{messageEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="recipientId">Recipient Id</span>
            </dt>
            <dd>{messageEntity.recipientId}</dd>
            <dt>
              <span id="postedByMemberId">Posted By Member Id</span>
            </dt>
            <dd>{messageEntity.postedByMemberId}</dd>
            <dt>
              <span id="messageBody">Message Body</span>
            </dt>
            <dd>{messageEntity.messageBody}</dd>
            <dt>
              <span id="whenCreated">When Created</span>
            </dt>
            <dd>
              <TextFormat value={messageEntity.whenCreated} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Project</dt>
            <dd>{messageEntity.project ? messageEntity.project.id : ''}</dd>
            <dt>Recipient Item</dt>
            <dd>{messageEntity.recipientItem ? messageEntity.recipientItem.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/message" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/message/${messageEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ message }: IRootState) => ({
  messageEntity: message.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MessageDetail);
