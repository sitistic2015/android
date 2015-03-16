from flask import Flask, jsonify, request
from flask_restful import abort
import rospy
from geometry_msgs.msg import Pose
from geometry_msgs.msg import Point

app = Flask(__name__)

class Command:
    def __init__(self):
        self.cmd = rospy.Publisher("/waypoint", Pose, queue_size=10, latch=True)
        rospy.init_node("cat_node")

    def setWaypoint(self, x, y, z):
        pose = Pose(position=Point(x,y,z))
        self.cmd.publ
        
command = Command()

@app.route('/robot/rotate', methods=['POST'])
def rotate() :
    if not request.json or not 'd' in request.json:
        abort(400)
    
    d = request.json['d']
    
    return jsonify({"d": d}), 201

@app.route('/robot/waypoint', methods=['POST'])
def waypoint():
    if not request.json or not 'x' in request.json or not 'y' in request.json or not 'z' in request.json:
        abort(400)
    
    x = request.json['x']
    y = request.json['y']
    z = request.json['z']
    
    command.setWaypoint(x,y,z)
    
    return jsonify({"x": x, "y": y, "z": z}), 201

if __name__ == '__main__' :
    app.run(debug=True, host='0.0.0.0')