import com.pokejava.*;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;
import java.net.URL;
import javax.imageio.ImageIO;

public class PokedexFrame extends JFrame{

	private Sprite sprite;
	private JList<String> listPokemons;
	private JLabel lblName,lblAtk,lblDef,lblSpAtk,lblSpDef,lblHP,lblSpeed,lblSprite;
	private JTextArea txtDescription;
	private JPanel mainPanel,pokeInfo;
	private DefaultListModel<String> listModelPokemons;
	private JScrollPane listScrollPane = new JScrollPane();
	private Pokemon pokemon;
	
	public PokedexFrame(){
		super("Pokedex");
		mainPanel = new JPanel();
		pokeInfo = new JPanel();
		pokeInfo.setLayout(new BoxLayout(pokeInfo,BoxLayout.Y_AXIS));
		listPokemons = new JList();
		listPokemons.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPokemons.setVisibleRowCount(20);
		listScrollPane.setViewportView(listPokemons);
		panelInfoPokemon(pokemon);
		addListenerToList();
		mainPanel.add(listScrollPane);
		mainPanel.add(pokeInfo);
		setModelPokemons();
		setContentPane(mainPanel);
        setSize(800,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void setModelPokemons(){
		try{
			Pokedex pokedex = new Pokedex();
			listModelPokemons = new DefaultListModel();
			for(String p: pokedex.getPokemonsNames()){
				listModelPokemons.addElement(p);
			}
			listPokemons.setModel(listModelPokemons);
		}catch(Exception e){
			System.out.println("Fallo la red");
		}
	}

	public static void main(String args[]){
		new PokedexFrame();
	}

	public void panelInfoPokemon(Pokemon pokemon){
		if(pokemon==null){
			
			lblSprite = new JLabel("");
			pokeInfo.add(lblSprite);
			lblName = new JLabel("");
			lblHP = new JLabel("");
			lblAtk = new JLabel("");
			lblDef = new JLabel("");
			lblSpeed = new JLabel("");
			lblSpAtk = new JLabel("");
			lblSpDef = new JLabel("");
			txtDescription = new JTextArea("",10,50);

			pokeInfo.add(new JLabel("Name: "));
			pokeInfo.add(lblName);
			pokeInfo.add(new JLabel("HP: "));
			pokeInfo.add(lblHP);
			pokeInfo.add(new JLabel("Atk: "));
			pokeInfo.add(lblAtk);
			pokeInfo.add(new JLabel("Def: "));
			pokeInfo.add(lblDef);
			pokeInfo.add(new JLabel("Speed: "));
			pokeInfo.add(lblSpeed);
			pokeInfo.add(new JLabel("Sp.Atk: "));
			pokeInfo.add(lblSpAtk);
			pokeInfo.add(new JLabel("Sp.Def: "));
			pokeInfo.add(lblSpDef);
			pokeInfo.add(txtDescription);
		}
		else{
			try{
				lblSprite.setIcon(new ImageIcon(ImageIO.read(new URL("http://www.pokeapi.co"+sprite.getImage()))));

			}
			catch(Exception e){
				System.out.println("Error en el request");
			}
			System.out.println(sprite.getImage());
			lblName.setText(pokemon.getName());
			lblHP.setText(pokemon.getHP()+"");
			lblAtk.setText(pokemon.getAttack()+"");
			lblDef.setText(pokemon.getDefense()+"");
			lblSpeed.setText(pokemon.getSpeed()+"");
			lblSpAtk.setText(pokemon.getSpAttack()+"");
			lblSpDef.setText(pokemon.getSpDefense()+"");
		}
	}

	public void addListenerToList(){
		ListSelectionListener listListener = new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				if(!e.getValueIsAdjusting()){
					pokemon = new Pokemon(listPokemons.getSelectedValue().toString());
					sprite = new Sprite(listPokemons.getSelectedIndex()+2);
					System.out.println(listPokemons.getSelectedIndex()+1);
					panelInfoPokemon(pokemon);
					System.out.println(listPokemons.getSelectedValue().toString());
				}
			}
		};
		listPokemons.addListSelectionListener(listListener);
	}
}