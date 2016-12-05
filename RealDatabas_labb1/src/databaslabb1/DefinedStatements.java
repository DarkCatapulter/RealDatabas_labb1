/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author swehu
 */
public class DefinedStatements {

    DBConnection dbcon = null;

    public DefinedStatements(DBConnection connection) {
        dbcon = connection;
    }

    public boolean login(String email, String password) {
        try {
            PreparedStatement stmt = dbcon.con.prepareStatement("SELECT COUNT(*) AS c FROM user WHERE Email = ? AND Password = Password(?);");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("c") == 1;
        } catch (SQLException ex) {
            Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean register(String email, String password) {
        try {
            PreparedStatement stmt = dbcon.con.prepareStatement("INSERT INTO user(Email, Password) VALUES(?,Password(?));");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("c") == 1;
        } catch (SQLException ex) {
            Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<Content> searchContent(String title, String genre, String creator, int rating) {
        ArrayList<Content> tmpArr = new ArrayList<>();

        try {
            PreparedStatement stmt = dbcon.con.prepareStatement("SELECT * FROM ("
                    + "SELECT content.ContentID, content.Type, content.Title, content.AddedBy, content.ReleaseDate, GROUP_CONCAT(creator.CreatorID) AS creatorID, GROUP_CONCAT(creator.Role) AS creatorRole, GROUP_CONCAT(creator.Nation) AS creatorNations, GROUP_CONCAT(creator.AddedBy) AS creatorAddedBy, GROUP_CONCAT(creator.Name) AS creatorName, GROUP_CONCAT(DISTINCT contentgenre.Genre) AS genre, AVG(review.Score) AS avgScore FROM content, contentgenre, madeby, creator, review WHERE content.contentID = contentgenre.contentID AND( madeby.contentID = content.contentID AND creator.creatorID = madeby.creatorID ) AND content.ContentID = review.ContentID GROUP BY content.ContentID) AS allContent"
                    + " WHERE Title LIKE ? AND genre LIKE ? AND creatorName LIKE ? AND avgScore LIKE ?;");
            stmt.setString(1, "%" + title + "%");
            stmt.setString(2, "%" + genre + "%");
            stmt.setString(3, "%" + creator + "%");
            if (rating > 0) {
                stmt.setString(4, rating + "%");
            } else {
                stmt.setString(4, "%");
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] creatorIDs = rs.getString("CreatorID").split(",");
                String[] creatorNames = rs.getString("CreatorName").split(",");
                String[] creatorRoles = rs.getString("CreatorRole").split(",");
                String[] creatorNations = rs.getString("CreatorNations").split(",");
                String[] creatorAddedBy = rs.getString("CreatorAddedBy").split(",");
                String[] genres = rs.getString("genre").split(",");
                Content tmp = new Content(rs.getInt("ContentID"), ContentType.valueOf(rs.getString("Type").toUpperCase()), rs.getString("Title"), rs.getTimestamp("ReleaseDate"), rs.getString("AddedBy"), rs.getString("avgScore"));
                int i = 0;
                for (String creatorID : creatorIDs) {
                    if (!tmp.creatorExists(Integer.parseInt(creatorID))) {
                        tmp.addCreator(new Creator(Integer.parseInt(creatorID), creatorNames[i], creatorRoles[i], creatorNations[i], creatorAddedBy[i]));
                    }
                    i++;
                }
                for (String genre1 : genres) {
                    tmp.addGenre(genre1);
                }
                tmpArr.add(tmp);
            }

        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmpArr;
    }

    public void addContent(Content content) {
        try {
            dbcon.con.setAutoCommit(false);
            PreparedStatement stmt = dbcon.con.prepareStatement("INSERT INTO content(Type, Title, ReleaseDate, AddedBy) VALUES(?,?,?,?)");
            stmt.setString(1, content.getType().getValue());
            stmt.setString(2, content.getTitle());
            stmt.setTimestamp(3, content.getTimestamp());
            stmt.setString(4, content.getAddedBy());
            stmt.executeUpdate();

            stmt = dbcon.con.prepareStatement("INSERT INTO creator(Role, Name, Nation, addedBy) VALUES(?,?,?,?)");
            for (Creator creator : content.getCreators()) {
                stmt.setString(1, creator.getRole());
                stmt.setString(2, creator.getName());
                stmt.setString(3, creator.getNation());
                stmt.setString(4, creator.getAddedBy());
                stmt.executeUpdate();
            }

            //Lägg till genre
            dbcon.con.commit();

        } catch (SQLException ex) {
            try {
                dbcon.con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dbcon.con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
