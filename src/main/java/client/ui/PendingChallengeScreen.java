package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.ChallengeRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PendingChallengeScreen extends Screen {
    private JPanel frame;
    private JButton backButton;
    private JPanel pane;

    @Override
    public void start() {
        super.start();

        ResponseBody response = new ResponseBody();
        RequestBody request = new RequestBody();

        List<ChallengeRequest> challengeList = new ArrayList<ChallengeRequest>();

        if (Session.isOperator()) {
            ResponseBody res = Client.request("challenge/getOperatorChallenges", request);

            if (res.getField("list") == null) {
                return;
            }

            challengeList = (List<ChallengeRequest>) res.getField("list");
        } else {
            request.addField("clazz", ChallengeRequest.class);

            ResponseBody res = Client.request("item/getAll", request);

            if (res.getField("data") == null) {
                return;
            }

            challengeList = ((List<ChallengeRequest>) res.getField("data")).stream().filter(e -> Objects.equals(e.getAttackedId(), Session.getCurrentUser().getId())).toList();
        }

        pane.setLayout(new GridLayout(challengeList.size(), 4));
        for (int i = 0; i < challengeList.size(); i++)
        {
            ChallengeRequest challenge = challengeList.get(i);
            Label label = new Label();
            Label bet = new Label();
            Button accept = new Button();
            Button decline = new Button();

            label.setText("VS " + challenge.getAttackerId());
            pane.add(label);

            bet.setText(Integer.toString(challenge.getBet()));
            pane.add(bet);

            accept.setLabel("Accept");
            accept.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String endpoint = "item/acceptChallengeFromPlayer";

                    if (Session.isOperator()) {
                        endpoint = "item/acceptChallengeFromOperator";
                    }

                    RequestBody request = new RequestBody();
                    request.addField("challenge", challenge);

                    ResponseBody response = Client.request(endpoint, request);
                    pane.remove(label);
                    pane.remove(bet);
                    pane.remove(accept);
                    pane.remove(decline);
                }
            });
            pane.add(accept);

            decline.setLabel("Decline");
            decline.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String endpoint = "item/denyChallengeFromPlayer";

                    if (Session.isOperator()) {
                        endpoint = "item/denyChallengeFromOperator";
                    }

                    RequestBody request = new RequestBody();
                    request.addField("challenge", challenge);

                    ResponseBody response = Client.request(endpoint, request);
                    pane.remove(label);
                    pane.remove(bet);
                    pane.remove(accept);
                    pane.remove(decline);
                }
            });
            pane.add(decline);
        }
    }

    public PendingChallengeScreen() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.goBack();
            }
        });
    }

    public JPanel getPanel() {
        return this.frame;
    }
}
