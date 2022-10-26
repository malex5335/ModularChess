package de.riagade.modular.chess.gui;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.util.Piece;
import lombok.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

@Getter
@Setter
public class GuiWindow extends JFrame {
	private JPanel contentPane;
	private Board board;
	private Map<Piece, JButton> buttonMap = new HashMap<>();
	private GuiGame.Move move;
	private boolean buttonsDone;
	private static final Color DARK_BACKGROUND = new Color(220, 255, 220);
	private static final Color LIGHT_BACKGROUND = new Color(50, 100, 50);
	private static final int FIELD_SIZE = 100;
	private static final int PADDING_TOP = 30;
	private static final int BORDER_SIZE = 8;

	public GuiWindow(Board board) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, FIELD_SIZE*8 + BORDER_SIZE, FIELD_SIZE*8 + PADDING_TOP);
		setContentPane(new JPanel());
		getContentPane().setLayout(new BorderLayout(0, 0));
		Collections.sort(board.getAllPositions());
		setBoard(board);
	}

	@Override
	public void paint(Graphics g) {
		var x = 0;
		var y = PADDING_TOP;
		var background = LIGHT_BACKGROUND;
		for(var position : getBoard().getAllPositions()) {
			drawBackground(g, x, y, background);
			var optionalPiece = getBoard().getPiece(position);
			if(optionalPiece.isPresent()) {
				drawPiece(g, x, y, background, optionalPiece.get());
			}
			drawCoordsInfo(g, x, y, background, position);
			x += FIELD_SIZE;
			if(position.x() == 'H') {
				x = 0;
				y += FIELD_SIZE;
				background = getNextBackground(background);
			}
			background = getNextBackground(background);
		}
		setButtonsDone(true);
	}

	private static void drawBackground(Graphics g, int x, int y, Color background) {
		g.setColor(background);
		g.fillRect(x, y, FIELD_SIZE, FIELD_SIZE);
	}

	private void drawPiece(Graphics g, int x, int y, Color background, Piece piece) {
		var pieceImage = getImage(piece);
		if(pieceImage != null) {
			var diffWidth = FIELD_SIZE - pieceImage.getWidth(null);
			var diffHeight = FIELD_SIZE - pieceImage.getHeight(null);
			g.drawImage(pieceImage, x + diffWidth / 2, y + diffHeight / 2, background, null);
		} else {
			var button = getButtonMap().getOrDefault(piece, new JButton());
			button.paintImmediately(x, y, FIELD_SIZE, FIELD_SIZE);
			button.setBackground(background);
			button.setBorder(null);
			button.setText(String.valueOf(piece.getPieceType().getValue()));
			getButtonMap().put(piece, button);
		}
	}

	private void drawCoordsInfo(Graphics g, int x, int y, Color background, BoardPosition position) {
		if(x == 0) {
			var padding = 15;
			g.setColor(getNextBackground(background));
			g.drawString(String.valueOf(position.y()), x + padding, y + padding);
		}
		if(position.y() == 1) {
			var padding = 15;
			g.setColor(getNextBackground(background));
			g.drawString(String.valueOf(position.x()), x + FIELD_SIZE - padding, y + FIELD_SIZE - padding);
		}
	}

	private Color getNextBackground(Color color) {
		return color.equals(DARK_BACKGROUND) ? LIGHT_BACKGROUND : DARK_BACKGROUND;
	}

	private Image getImage(Piece piece) {
		var pieceValue = piece.getPieceType().getValue();
		var playerValue = piece.getPlayer().getValue();
		var imageName = String.valueOf(new char[]{ playerValue, pieceValue }) + ".png";
		try {
			var url = getClass().getClassLoader().getResource(imageName);
			if(url != null)
				return ImageIO.read(new File(url.toURI()));
		} catch (URISyntaxException | IOException ignored) {}
		return null;
	}

	public List<JButton> getButtons(Player player) {
		return getButtonMap().entrySet().stream()
				.filter(e -> player.equals(e.getKey().getPlayer()))
				.map(Map.Entry::getValue)
				.toList();
	}
}
